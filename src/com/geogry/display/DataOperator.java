package com.geogry.display;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DataOperator {

	//邻接矩阵
	private double[][] adjacent_matrix;
	
	//网络中边的最大编号
	private int max_id = 0;
	
	//网络中边的总数
	private int edgeCount = 0;
	
	public double[][] readFile(String filename){
		File dataFile = new File(filename);
		if(dataFile.isFile() && dataFile.exists()){
			try {
				InputStreamReader reader = new InputStreamReader(new FileInputStream(dataFile));
				BufferedReader br = new BufferedReader(reader);
				String lineText = null;
				int a,b;
				List<int[]> indexs = new ArrayList<int[]>();
				while((lineText = br.readLine()) != null){//寻找最大的下标值
					String[] num = lineText.split("\t");
					a = Integer.parseInt(num[0]);
					b = Integer.parseInt(num[1]);
					if(a > b && a > max_id)
						max_id = a;
					else if(b > a && b > max_id)
						max_id = b;
					indexs.add(new int[]{a, b});
				}
				System.out.println("max_id:" + max_id);
				br.close();
				reader.close();
				adjacent_matrix = new double[max_id + 1][max_id + 1];//构建邻接矩阵
				edgeCount = indexs.size();
				//对称文件与非对称文件的区别
				//edgeCount = 2 * edgeCount;
				System.out.println("edgeCount:" + edgeCount);
				for(int i = 0; i < indexs.size(); i++){
					adjacent_matrix[indexs.get(i)[0]][indexs.get(i)[1]] = 1.0/this.edgeCount;
					adjacent_matrix[indexs.get(i)[1]][indexs.get(i)[0]] = 1.0/this.edgeCount;
				}
				for(int i = 1; i <= max_id; i++){
					adjacent_matrix[i][0] = adjacent_matrix[0][i] = i;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("文件读取错误！");
				System.exit(1);
			}
		} else{
			System.out.println("文件不存在！");
			System.exit(1);
		}
		System.out.println();
		return adjacent_matrix;
	}
	
	public void writeFile(String filename, List<int[]> result){
		try {
			File resultFile = new File(filename);
			if(!resultFile.exists())
				resultFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(resultFile);
			PrintWriter pw = new PrintWriter(fos);
			for(int i = 0; i < result.size(); i++)
				pw.write(result.get(i)[0] + " " + result.get(i)[1] + " " + result.get(i)[2] + System.getProperty("line.separator"));
			pw.flush();
			pw.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeFile(String filename, List result, int flag){
		try {
			File resultFile = new File(filename);
			if(!resultFile.exists())
				resultFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(resultFile);
			PrintWriter pw = new PrintWriter(fos);
			for(int i = 0; i < result.size(); i++)
				pw.write((i + 1) + " " + result.get(i) + System.getProperty("line.separator"));
			pw.flush();
			pw.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMax_id() {
		return max_id;
	}

	public int getEdgeCount() {
		return edgeCount;
	}

	public void writeFile(String filename, int[][] clusters) {
		try {
			File resultFile = new File(filename);
			if(!resultFile.exists())
				resultFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(resultFile);
			PrintWriter pw = new PrintWriter(fos);
			int[] community = ToolUtils.unique(clusters[1]);
			for(int i = 0; i < community.length; i++){
				for(int j = 0; j < clusters[0].length; j++)
					if(clusters[1][j] == community[i])
						pw.write(clusters[0][j] + "\t");
				pw.write(System.getProperty("line.separator"));
				pw.write(System.getProperty("line.separator"));
			}
			pw.flush();
			pw.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
