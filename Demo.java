package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Demo {

	static class Job implements Comparable<Job>{
	    int start;
	    int end;
	    int profit;
	    
	    Job(int start, int end, int profit){
	        this.start = start;
	        this.end = end;
	        this.profit = profit;
	    }
	    
	    @Override
	    public int compareTo(Job other){
	        return Integer.compare(this.end, other.end);
	    }
	}

	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Enter the number of Jobs:");
	    int n = sc.nextInt();
	    List<Job> jobs = new ArrayList<Job>();
	    for(int i=0; i<n; i++){
	        System.out.println("Enter job start time, end time, and earnings:");
	        int start = sc.nextInt();
	        int end = sc.nextInt();
	        int profit = sc.nextInt();
	        jobs.add(new Job(start, end, profit));
	    }
	    Collections.sort(jobs);
	    int[] dp = new int[n];
	    dp[0] = jobs.get(0).profit;
	    for(int i=1; i<n; i++){
	        int currProfit = jobs.get(i).profit;
	        int latestNonConflictJob = findLatestNonConflictJob(jobs, i);
	        if(latestNonConflictJob != -1){
	            currProfit += dp[latestNonConflictJob];
	        }
	        dp[i] = Math.max(currProfit, dp[i-1]);
	    }
	    int maxProfit = dp[n-1];
	    int numLeftJobs = n - getMaxProfitJobsCount(dp, jobs);
	    int otherEmployeesEarnings = getTotalEarnings(jobs) - maxProfit;
	    System.out.println("Number of jobs left: " + numLeftJobs);
	    System.out.println("Earnings of other employees: " + otherEmployeesEarnings);
	}

	static int findLatestNonConflictJob(List<Job> jobs, int i){
	    for(int j=i-1; j>=0; j--){
	        if(jobs.get(j).end <= jobs.get(i).start){
	            return j;
	        }
	    }
	    return -1;
	}

	static int getMaxProfitJobsCount(int[] dp, List<Job> jobs){
	    int i = dp.length-1;
	    while(i > 0 && dp[i] == dp[i-1]){
	        i--;
	    }
	    int count = 0;
	    for(int j=i; j>=0; j--){
	        if(jobs.get(j).profit > 0 && dp[i] == dp[j]){
	            count++;
	        }
	    }
	    return count;
	}

	static int getTotalEarnings(List<Job> jobs){
	    int totalEarnings = 0;
	    for(Job job : jobs){
	        totalEarnings += job.profit;
	    }
	    return totalEarnings;
	}

	}


