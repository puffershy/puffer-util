package com.puffer.util;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.testng.annotations.Test;

import com.puffer.util.lang.time.DateUtil;

public class FileUtilTest {

	@Test
	public void copy() throws IOException {

		String pathPrefix = "D://";
		String mchnId = "0002900F0354237";
		String idNo = "350322196601012534";

		String imageFilePath = "/test/image/buyilogo.png";

		String dateStr = DateUtil.formatDate(new Date(), "yyyyMMdd");

		File imageFile = new File(imageFilePath);
		// 图片后缀
		String imageFileSuffix = imageFile.getName().substring(imageFile.getName().lastIndexOf("."));

		String newImageFileName = mchnId.concat(idNo).concat(dateStr).concat(imageFileSuffix);

		Path imagePath = Paths.get(pathPrefix, imageFilePath);

		Path newImagePath = Paths.get(pathPrefix, imageFile.getAbsolutePath(), newImageFileName);

		// 复制文件
		Path copy = Files.copy(imagePath, newImagePath);

		System.out.println(imageFile.getAbsolutePath().concat(newImageFileName));
	}

}
