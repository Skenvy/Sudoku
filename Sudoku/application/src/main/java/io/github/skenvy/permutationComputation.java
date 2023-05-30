package io.github.skenvy;

import java.util.ArrayList;
import java.util.Arrays;

public class permutationComputation {
	
	ArrayList<int[]> permutations;
	
	public permutationComputation(){
		permutations = new ArrayList<int[]>();
	}
	
	public static void main(String[] args) {
		permutationComputation pc = new permutationComputation();
		pc.permutationCompute(1, 5);
		pc.clear();
		pc.permutationCompute(2, 5);
		for(int k = 0; k < pc.permutations.size(); k++){
			System.out.println(Arrays.toString(pc.permutations.get(k)));
		}
	}
	
	public void permutationCompute(int subsetSize, int overHowMany){
		int[] carry = new int[subsetSize];
		permutationComputationRoll(subsetSize,overHowMany,0, carry);
	}
	
	public void permutationComputationRoll(int subsetSize, int overHowMany, int level, int[] carry){
		if(level == subsetSize){
			permutations.add(carry.clone());
		} else {
			for(int k = level; k < overHowMany; k++){
				if(level == 0){
					carry[0] = k;
					permutationComputationRoll(subsetSize,overHowMany,1, carry);
				} else {
					if(k > carry[level-1]){
						carry[level] = k;
						permutationComputationRoll(subsetSize,overHowMany,level+1, carry);
					}
				}
			}
		}
	}
	
	public void clear(){
		permutations.clear();
	}
	
	public int size(){
		return permutations.size();
	}
	
	public int[] get(int index){
		return permutations.get(index);
	}
	
	public int getget(int index1, int index2){
		return permutations.get(index1)[index2];
	}
}
