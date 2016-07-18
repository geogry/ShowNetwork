package com.geogry.display;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataInput {
	
	private int max_id = 0;
	
	private Object[][] vertexs = null;
	
	private List<int[]> edges = null;

	public void readFile1(String src, String result){
		File srcFile = new File(src);
		File resultFile = new File(result);
		if(srcFile.isFile() && srcFile.exists()){
			try {
				//读源文件
				InputStreamReader reader = new InputStreamReader(new FileInputStream(srcFile));
				BufferedReader br = new BufferedReader(reader);
				String lineText = null;
				int a,b;
				edges = new ArrayList<int[]>();
				while((lineText = br.readLine()) != null){//寻找最大的下标值
					String[] num = lineText.split("\t");
					a = Integer.parseInt(num[0]);
					b = Integer.parseInt(num[1]);
					if(a > b && a > max_id)
						max_id = a;
					else if(b > a && b > max_id)
						max_id = b;
					if(a < b)
						edges.add(new int[]{a, b});
				}
				br.close();
				reader.close();
				
				vertexs = new Object[max_id][3];
				Map<Integer, String> notAgainPoint = new HashMap<Integer, String>();
				Map<Integer, String> notAgainColor = new HashMap<Integer, String>();
				//读社区文件
				reader = new InputStreamReader(new FileInputStream(resultFile));
				br = new BufferedReader(reader);
				int value = 0;
				int comCount = 0;
				while((lineText = br.readLine()) != null){
					String[] indexs = lineText.split("\t");
					Color color;
					if(!lineText.equals(""))
						comCount++;
					while(true){//产生新颜色
						int r = (int)(Math.random() * 255);
						int g = (int)(Math.random() * 255);
						int b1 = (int)(Math.random() * 255);
						String rgb = r + " " + g + " " + b1;
						if(!notAgainColor.containsValue(rgb)){
							notAgainColor.put(value, rgb);
							color = new Color(r, g, b1);
							value++;
							break;
						}
					}
					int count = 1;
					if(comCount <= 3)
						for(int i = 0; i < indexs.length; i++){
							if(indexs[i] != null && !indexs[i].equals("")){
								vertexs[Integer.parseInt(indexs[i]) - 1][2] = color;
								//为每个节点产生坐标
								while(true){
									int x = (int)(Math.random() * 200) + (comCount-1)*250 + 50;
									int y = (int)(Math.random() * 200) + 50;
									if(!notAgainPoint.containsValue(x + " " + y)){
										notAgainPoint.put(count, x + " " + y);
										vertexs[Integer.parseInt(indexs[i])-1][0] = x;
										vertexs[Integer.parseInt(indexs[i])-1][1] = y;
										count++;
										break;
									}
								}
							}
						}
					else
						for(int i = 0; i < indexs.length; i++){
							if(indexs[i] != null && !indexs[i].equals("")){
								vertexs[Integer.parseInt(indexs[i]) - 1][2] = color;
								//为每个节点产生坐标
								while(true){
									int x = (int)(Math.random() * 200) + (comCount - 4)*250 + 50;
									int y = (int)(Math.random() * 200) + 300;
									if(!notAgainPoint.containsValue(x + " " + y)){
										notAgainPoint.put(count, x + " " + y);
										vertexs[Integer.parseInt(indexs[i])-1][0] = x;
										vertexs[Integer.parseInt(indexs[i])-1][1] = y;
										count++;
										break;
									}
								}
							}
						}
					notAgainPoint.clear();;
				}
				notAgainColor = null;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("文件读取错误！");
				System.exit(1);
			}
		} else{
			System.out.println("文件不存在！");
			System.exit(1);
		}
	}
	
	public void readFile2(String src, String result){
		File srcFile = new File(src);
		File resultFile = new File(result);
		if(srcFile.isFile() && srcFile.exists()){
			try {
				//读源文件
				InputStreamReader reader = new InputStreamReader(new FileInputStream(srcFile));
				BufferedReader br = new BufferedReader(reader);
				String lineText = null;
				int a,b;
				edges = new ArrayList<int[]>();
				while((lineText = br.readLine()) != null){//寻找最大的下标值
					String[] num = lineText.split("\t");
					a = Integer.parseInt(num[0]);
					b = Integer.parseInt(num[1]);
					if(a > b && a > max_id)
						max_id = a;
					else if(b > a && b > max_id)
						max_id = b;
					if(a < b)
						edges.add(new int[]{a, b});
				}
				br.close();
				reader.close();
				
				vertexs = new Object[max_id][3];
				Map<Integer, String> notAgainPoint = new HashMap<Integer, String>();
				Map<Integer, String> notAgainColor = new HashMap<Integer, String>();
				//读社区文件
				reader = new InputStreamReader(new FileInputStream(resultFile));
				br = new BufferedReader(reader);
				int value = 0;
				int comCount = 0;
				while((lineText = br.readLine()) != null){
					String[] indexs = lineText.split("\t");
//					for(int i = 0; i < indexs.length; i++)
//						System.out.print(indexs[i] + " ");
//					System.out.println();
					Color color;
					if(!lineText.equals(""))
						comCount++;
					else
						continue;
					while(true){//产生新颜色
						int r = (int)(Math.random() * 255);
						int g = (int)(Math.random() * 255);
						int b1 = (int)(Math.random() * 255);
						String rgb = r + " " + g + " " + b1;
						if(!notAgainColor.containsValue(rgb)){
							notAgainColor.put(value, rgb);
							color = new Color(r, g, b1);
							value++;
							break;
						}
					}
					int count = 1;
					if(comCount <= 2)
						for(int i = 0; i < indexs.length; i++){
							if(indexs[i] != null && !indexs[i].equals("")){
								vertexs[Integer.parseInt(indexs[i]) - 1][2] = color;
								//为每个节点产生坐标
								while(true){
									int x = (int)(Math.random() * 300) + (comCount-1)*350 + 50;
									int y = (int)(Math.random() * 200) + 50;
									if(!notAgainPoint.containsValue(x + " " + y)){
										notAgainPoint.put(count, x + " " + y);
										vertexs[Integer.parseInt(indexs[i])-1][0] = x;
										vertexs[Integer.parseInt(indexs[i])-1][1] = y;
										count++;
										break;
									}
								}
							}
							//System.out.println(vertexs[Integer.parseInt(indexs[i]) - 1][0] + " " + vertexs[Integer.parseInt(indexs[i]) - 1][1] + " " + vertexs[Integer.parseInt(indexs[i]) - 1][2]);
						}
					else
						for(int i = 0; i < indexs.length; i++){
							if(indexs[i] != null && !indexs[i].equals("")){
								vertexs[Integer.parseInt(indexs[i]) - 1][2] = color;
								//为每个节点产生坐标
								while(true){
									int x = (int)(Math.random() * 400) + (comCount - 3)*250 + 200;
									int y = (int)(Math.random() * 200) + 300;
									if(!notAgainPoint.containsValue(x + " " + y)){
										notAgainPoint.put(count, x + " " + y);
										vertexs[Integer.parseInt(indexs[i])-1][0] = x;
										vertexs[Integer.parseInt(indexs[i])-1][1] = y;
										count++;
										break;
									}
								}
							}
							//System.out.println(vertexs[Integer.parseInt(indexs[i]) - 1][0] + " " + vertexs[Integer.parseInt(indexs[i]) - 1][1] + " " + vertexs[Integer.parseInt(indexs[i]) - 1][2]);
						}
					notAgainPoint.clear();;
				}
				notAgainColor = null;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("文件读取错误！");
				System.exit(1);
			}
		} else{
			System.out.println("文件不存在！");
			System.exit(1);
		}
	}

	public int getMax_id() {
		return max_id;
	}

	public Object[][] getVertexs() {
		return vertexs;
	}

	public List<int[]> getEdges() {
		return edges;
	}
}
