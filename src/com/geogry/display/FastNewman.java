package com.geogry.display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastNewman {
	
	private List<int[]> Z = null;
	
	private int[][] clusters = null;
	
	private double[][] deltaQs = null;
	
	private int max_id;
	
	private int vCount;
	
	private int n;
	
	private List Qs;
	
	public FastNewman(double[][] adjacent_matrix, int max_id, int edgeCount){
		this.n = max_id;
		this.vCount = n;
		this.max_id = max_id;
		this.clusters = new int[2][max_id];
		this.Qs = new ArrayList();
		for(int i = 1; i <= max_id; i++){
			clusters[0][i-1] = i;
			clusters[1][i-1] = i;
		}
		double[] a = new double[max_id + 1];
		double[][] e = adjacent_matrix;
		double Q = 0;
		this.deltaQs = adjacent_matrix;
		for(int i=1; i <= this.n; i++){
			for(int j=i;j<=this.n;j++){
				if(e[i][j] != 0)
					a[i] += e[i][j];
				if(i!=j)
					a[j] += e[i][j];
			}
			Q += e[i][i] + a[i]*a[i];
		}
		for(int i = 1; i <= this.n; i++){
			for(int j = i + 1; j <= this.n; j++){
				deltaQs[i][j] = deltaQs[j][i] = 2*(e[i][j] - a[i]*a[j]);
				System.out.print(deltaQs[i][j] + "\t");
			}
			System.out.println();
		}
		deltaQs[0][0] = -100;
		Qs.add(Q);
		Z = new ArrayList<int[]>();
		//System.out.println(deltaQs[33][34]);
	}
	
	public List<int[]> mFastNewman(){
		do{
			List<int[]> maxDeltaQs = new ArrayList<int[]>();
			int x = 0, y = 0;
			double maxDeltaQ = 0;
			//快速排序找最大值，先行合并成1列，再找列最大
			for(int i = 1; i <= this.vCount; i++){
				if(deltaQs[i][0] == 0)//排除已经被合并的社区
					continue;
				for(int j = i + 1; j <= this.vCount; j++){
					if(deltaQs[0][j] == 0)
						continue;
					if(deltaQs[i][j] > deltaQs[x][y]){
						x=i;
						y=j;
						maxDeltaQ = deltaQs[i][j];
						maxDeltaQs.clear();
						maxDeltaQs.add(new int[]{i, j});
					} else if(deltaQs[i][j] == deltaQs[x][y]){
						maxDeltaQs.add(new int[]{i, j});
					}
				}
			}
			for(int i = 0; i < maxDeltaQs.size(); i++){
				x = maxDeltaQs.get(i)[0];
				y = maxDeltaQs.get(i)[1];
				if(deltaQs[0][x] == 0 || deltaQs[0][y] == 0)
					continue;
				if(x == y)
					continue;
				Qs.add(Double.parseDouble(Qs.get(Qs.size() - 1).toString()) + deltaQs[x][y]);
				int communityI = (int) deltaQs[x][0];
				int communityJ = (int) deltaQs[y][0];
				//System.out.println("当前社区数目：" + n + "，合并社区编号：" + communityI + " " + communityJ + "，最大DeltaQ为：" + deltaQs[x][y]);
				this.max_id++;
				int k = 0;
				for(int j = 0; j < this.vCount; j++)
					if(clusters[1][j] == communityI || clusters[1][j] == communityJ){
						clusters[1][j] = this.max_id;
						k++;
					}
				for(int j = i + 1; j < maxDeltaQs.size(); j++){
					if(maxDeltaQs.get(j)[0] == x || maxDeltaQs.get(j)[0] == y)
						deltaQs[maxDeltaQs.get(j)[0]][0] = max_id;
					if(maxDeltaQs.get(j)[1] == x || maxDeltaQs.get(j)[1] == y)
						deltaQs[maxDeltaQs.get(j)[0]][0] = max_id;
				}
				n--;
				
				this.Z.add(new int[]{communityI, communityJ, k});
				this.refresh(x, y);
			}
			if(maxDeltaQ > 0)
				break;
		}while(true);
		return Z;
	}
	
	public void refresh(int x, int y){
		//性质推算过程，证明正确性
		//推算出时间复杂度
		deltaQs[0][x] = deltaQs[x][0] = this.max_id;
		deltaQs[0][y] = deltaQs[y][0] = 0;
		for(int i = 1; i <= this.vCount; i++){
			if(i != x)
				deltaQs[i][x] = deltaQs[x][i] = deltaQs[x][i] + deltaQs[y][i];
			else
				deltaQs[x][x] += 2*deltaQs[x][y]+deltaQs[y][y];
			deltaQs[y][i] = deltaQs[i][y] = 0;
		}
	}
	
	/**
	 * 从参数中的一维数组中分离出不具有重复元素的数组并进行升序排序
	 * @param number 一维数组
	 * @return List Integer的集合, 存储的是在原始数组中不重复的值
	 */
	public int[] unique(int[] number){
		List<Integer> results = new ArrayList<Integer>();
		results.add(number[0]);
		int j;
		for(int i = 1; i < number.length; i++){
			for(j = 0; j < results.size(); j++)
				if(number[i] == results.get(j))
					break;
			if(j == results.size())
				results.add(number[i]);
		}
		int [] b = new int[results.size()];
		for(int i = 0; i < b.length; i++)
			b[i] = results.get(i);
		Arrays.sort(b);
		return b;
	}
	
	/**
	 * 查找数组中含有某个值的位置集合，能够查找所有原始数
	 * 组中的值
	 * @param value int型值，需要寻找的值
	 * @param original 一个int型的一维数组，原始寻找数组
	 * @return int[] 一个一维数组, 其中含有找到在original中找到的值的下标
	 */
	public int[] find(int[] original, int value){
		List<Integer> results = new ArrayList<Integer>();
		for(int i = 1; i < original.length; i++){
			if(original[i] == value){
				results.add(i);
			}
		}
		int[] b = new int[results.size()];
		for(int i = 0; i < results.size(); i++)
			b[i] = results.get(i);
		
		return b;
	}
	
	/**
	 * 在List中查找最大值，返回其所在的下标
	 * @param deltaQs List 存储的是double[3]类型的对象，第三行存储的是真实值
	 * @return 最大值所在的下标
	 */
	public int getMaxValue(List<double[]> deltaQs){
		double maxValue = deltaQs.get(0)[2];
		int maxIndex = 0;
		for(int i = 0; i < deltaQs.size(); i++){
			if(maxValue < deltaQs.get(i)[2]){
				maxValue = deltaQs.get(i)[2];
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	public List getQs() {
		return Qs;
	}

	public int[][] getClusters() {
		return clusters;
	}
}
