package com.puffer.util;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;

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

	public static void main(String[] args) {

		Scanner  scanner = new Scanner(System.in);
		//step1.输入三个参数
		System.out.println("请输入参数a：");
		int a = scanner.nextInt();
		System.out.println("请输入参数b：");
		int b = scanner.nextInt();
		System.out.println("请输入参数c：");
		int c = scanner.nextInt();

		//step2.参数转换
		int max = 0;
		int min = 0;

		if(a>b){
			max =a;
			min =b;
		}else{
			max =b;
			min =a;
		}

		if(max < c){
			max = c;
		}

		if(min > c){
			min = c;
		}


//		max = Math.max(a,b);
//		min = Math.min(a,b);
//
//		max= Math.max(max,c);
//		min = Math.min(min,c);


		//step3.结果输出
		System.out.println("最大值："+max);
		System.out.println("最小值："+min);
	}



}
