package com.puffer.util.xml;

import java.io.IOException;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.MissingFieldException;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.core.ReferencingMarshallingContext;
import com.thoughtworks.xstream.core.util.ArrayIterator;
import com.thoughtworks.xstream.core.util.CustomObjectOutputStream;
import com.thoughtworks.xstream.core.util.Fields;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.mapper.Mapper;

public class IgnoreGenericClassConverter extends AbstractReflectionConverter {
	private static final String ELEMENT_NULL = "null";
	private static final String ELEMENT_DEFAULT = "default";
	private static final String ELEMENT_UNSERIALIZABLE_PARENTS = "unserializable-parents";
	private static final String ATTRIBUTE_CLASS = "class";
	private static final String ATTRIBUTE_SERIALIZATION = "serialization";
	private static final String ATTRIBUTE_VALUE_CUSTOM = "custom";
	private static final String ELEMENT_FIELDS = "fields";
	private static final String ELEMENT_FIELD = "field";
	private static final String ATTRIBUTE_NAME = "name";

	public IgnoreGenericClassConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
		super(mapper, reflectionProvider);
	}

	/**
	 * 判断序列化对象是否使用该转换器
	 */
	@Override
	public boolean canConvert(Class type) {
		return true;
	}

	/**
	 * 将对象序列化
	 * </p>
	 * source 需要转换的对象 writer 序列化输出对象 context 序列化上下文
	 */
	// @Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		System.out.println("----------" + source.getClass());
		String attributeName = mapper.aliasForSystemAttribute(ATTRIBUTE_SERIALIZATION);
		if (attributeName != null) {
			writer.addAttribute(attributeName, ATTRIBUTE_VALUE_CUSTOM);
		}

		// this is an array as it's a non final value that's accessed from an
		// anonymous inner class.
		final Class[] currentType = new Class[1];
		final boolean[] writtenClassWrapper = { false };

		CustomObjectOutputStream.StreamCallback callback = new CustomObjectOutputStream.StreamCallback() {

			public void writeToStream(Object object) {
				if (object == null) {
					writer.startNode(ELEMENT_NULL);
					writer.endNode();
				} else {
					ExtendedHierarchicalStreamWriterHelper.startNode(writer, mapper.serializedClass(object.getClass()),
							object.getClass());
					context.convertAnother(object);
					writer.endNode();
				}
			}

			public void writeFieldsToStream(Map fields) {
				ObjectStreamClass objectStreamClass = ObjectStreamClass.lookup(currentType[0]);

				writer.startNode(ELEMENT_DEFAULT);
				for (Iterator iterator = fields.keySet().iterator(); iterator.hasNext();) {
					String name = (String) iterator.next();
					if (!mapper.shouldSerializeMember(currentType[0], name)) {
						continue;
					}
					ObjectStreamField field = objectStreamClass.getField(name);
					Object value = fields.get(name);
					if (field == null) {
						throw new MissingFieldException(value.getClass().getName(), name);
					}
					if (value != null) {
						ExtendedHierarchicalStreamWriterHelper.startNode(writer,
								mapper.serializedMember(source.getClass(), name), value.getClass());
						if (field.getType() != value.getClass() && !field.getType().isPrimitive()) {
							String attributeName = mapper.aliasForSystemAttribute(ATTRIBUTE_CLASS);
							if (attributeName != null) {
								writer.addAttribute(attributeName, mapper.serializedClass(value.getClass()));
							}
						}
						context.convertAnother(value);
						writer.endNode();
					}
				}
				writer.endNode();
			}

			public void defaultWriteObject() {
				boolean writtenDefaultFields = false;

				ObjectStreamClass objectStreamClass = ObjectStreamClass.lookup(currentType[0]);

				if (objectStreamClass == null) {
					return;
				}

				ObjectStreamField[] fields = objectStreamClass.getFields();
				for (int i = 0; i < fields.length; i++) {
					ObjectStreamField field = fields[i];
					Object value = readField(field, currentType[0], source);
					if (value != null) {
						if (!writtenClassWrapper[0]) {
							writer.startNode(mapper.serializedClass(currentType[0]));
							writtenClassWrapper[0] = true;
						}
						if (!writtenDefaultFields) {
							writer.startNode(ELEMENT_DEFAULT);
							writtenDefaultFields = true;
						}
						if (!mapper.shouldSerializeMember(currentType[0], field.getName())) {
							continue;
						}

						Class actualType = value.getClass();
						ExtendedHierarchicalStreamWriterHelper.startNode(writer,
								mapper.serializedMember(source.getClass(), field.getName()), actualType);
						Class defaultType = mapper.defaultImplementationOf(field.getType());
						// if (!actualType.equals(defaultType)) {
						// String attributeName =
						// mapper.aliasForSystemAttribute(ATTRIBUTE_CLASS);
						// if (attributeName != null) {
						// writer.addAttribute(attributeName,
						// mapper.serializedClass(actualType));
						// }
						// }

						context.convertAnother(value);

						writer.endNode();
					}
				}
				if (writtenClassWrapper[0] && !writtenDefaultFields) {
					writer.startNode(ELEMENT_DEFAULT);
					writer.endNode();
				} else if (writtenDefaultFields) {
					writer.endNode();
				}
			}

			public void flush() {
				writer.flush();
			}

			public void close() {
				throw new UnsupportedOperationException(
						"Objects are not allowed to call ObjectOutputStream.close() from writeObject()");
			}
		};

		try {
			boolean mustHandleUnserializableParent = false;
			Iterator classHieararchy = hierarchyFor(source.getClass()).iterator();
			while (classHieararchy.hasNext()) {
				currentType[0] = (Class) classHieararchy.next();
				if (!Serializable.class.isAssignableFrom(currentType[0])) {
					mustHandleUnserializableParent = true;
					continue;
				} else {
					if (mustHandleUnserializableParent) {
						marshalUnserializableParent(writer, context, source);
						mustHandleUnserializableParent = false;
					}
					if (serializationMembers.supportsWriteObject(currentType[0], false)) {
						writtenClassWrapper[0] = true;
						writer.startNode(mapper.serializedClass(currentType[0]));
						if (currentType[0] != mapper.defaultImplementationOf(currentType[0])) {
							String classAttributeName = mapper.aliasForSystemAttribute(ATTRIBUTE_CLASS);
							if (classAttributeName != null) {
								writer.addAttribute(classAttributeName, currentType[0].getName());
							}
						}
						CustomObjectOutputStream objectOutputStream = CustomObjectOutputStream.getInstance(context,
								callback);
						serializationMembers.callWriteObject(currentType[0], source, objectOutputStream);
						objectOutputStream.popCallback();
						writer.endNode();
					} else if (serializationMembers.supportsReadObject(currentType[0], false)) {
						// Special case for objects that have readObject(), but
						// not writeObject().
						// The class wrapper is always written, whether or not
						// this class in the hierarchy has
						// serializable fields. This guarantees that
						// readObject() will be called upon deserialization.
						writtenClassWrapper[0] = true;
						writer.startNode(mapper.serializedClass(currentType[0]));
						if (currentType[0] != mapper.defaultImplementationOf(currentType[0])) {
							String classAttributeName = mapper.aliasForSystemAttribute(ATTRIBUTE_CLASS);
							if (classAttributeName != null) {
								writer.addAttribute(classAttributeName, currentType[0].getName());
							}
						}
						callback.defaultWriteObject();
						writer.endNode();
					} else {
						writtenClassWrapper[0] = false;
						callback.defaultWriteObject();
						if (writtenClassWrapper[0]) {
							writer.endNode();
						}
					}
				}
			}
		} catch (IOException e) {
			throw new StreamException("Cannot write defaults", e);
		}
	}

	// public void doMarshal(final Object source, final HierarchicalStreamWriter
	// writer,
	// final MarshallingContext context) {
	//
	// }

	protected void marshalUnserializableParent(final HierarchicalStreamWriter writer, final MarshallingContext context,
			final Object replacedSource) {
		writer.startNode(ELEMENT_UNSERIALIZABLE_PARENTS);
		super.doMarshal(replacedSource, writer, context);
		writer.endNode();
	}

	private Object readField(ObjectStreamField field, Class type, Object instance) {
		Field javaField = Fields.find(type, field.getName());
		return Fields.read(javaField, instance);
	}

	protected List hierarchyFor(Class type) {
		List result = new ArrayList();
		while (type != Object.class && type != null) {
			result.add(type);
			type = type.getSuperclass();
		}

		// In Java Object Serialization, the classes are deserialized starting
		// from parent class and moving down.
		Collections.reverse(result);

		return result;
	}

	/**
	 * 反序列化
	 */
	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
