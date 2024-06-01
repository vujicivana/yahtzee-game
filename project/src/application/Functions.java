package application;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Functions {
	public static int toInteger(String s) {
		int number = 0;
		for (int i = 0; i < s.length(); i++) 
			number += (s.charAt(i) - 48) * Math.pow(10, s.length() - i - 1);
		return number;
	}
	
	public static int countOnes(int[] dices) {
		int number = 0;
		for (int i = 0; i < dices.length; i++)
			if (dices[i] == 1)
				number++;
		return number;
	}
	
	public static int countTwos(int[] dices) {
		int number = 0;
		for (int i = 0; i < dices.length; i++)
			if (dices[i] == 2)
				number++;
		return number;
	}
	
	public static int countThrees(int[] dices) {
		int number = 0;
		for (int i = 0; i < dices.length; i++)
			if (dices[i] == 3)
				number++;
		return number;
	}
	
	public static int countFours(int[] dices) {
		int number = 0;
		for (int i = 0; i < dices.length; i++)
			if (dices[i] == 4)
				number++;
		return number;
	}
	
	public static int countFives(int[] dices) {
		int number = 0;
		for (int i = 0; i < dices.length; i++)
			if (dices[i] == 5)
				number++;
		return number;
	}
	
	public static int countSixes(int[] dices) {
		int number = 0;
		for (int i = 0; i < dices.length; i++)
			if (dices[i] == 6)
				number++;
		return number;
	}
	
	public static int[] countAll(int[] dices) {
		int number1 = countOnes(dices);
		int number2 = countTwos(dices);
		int number3 = countThrees(dices);
		int number4 = countFours(dices);
		int number5 = countFives(dices);
		int number6 = countSixes(dices);
		int[] numbers = new int[]  {number1, number2, number3, number4, number5, number6};
		return numbers;
	}
	
	public static int sum(int[] dices) {
		int sum = 0;
		for (int i = 0; i < dices.length; i++)
			sum += dices[i];
		return sum;
	}
	
	public static int ones(int[] dices) {
		int number = countOnes(dices);
		return number * 1;
	}
	
	public static int twos(int[] dices) {
		int number = countTwos(dices);
		return number * 2;
	}
	
	public static int threes(int[] dices) {
		int number = countThrees(dices);
		return number * 3;
	}
	
	public static int fours(int[] dices) {
		int number = countFours(dices);
		return number * 4;
	}
	
	public static int fives(int[] dices) {
		int number = countFives(dices);
		return number * 5;
	}
	
	public static int sixes(int[] dices) {
		int number = countSixes(dices);
		return number * 6;
	}
	
	public static int threeOfAKind(int[] dices) {
		boolean flag = false;
		int[] numbers = countAll(dices);
		for (int i = 0; i < numbers.length; i++) 
			if (numbers[i] == 3) {
				flag = true;
				break;
			}
		if (flag == true)
			return sum(dices);
		else
			return 0;
	}
	
	public static int fourOfAKind(int[] dices) {
		boolean flag = false;
		int[] numbers = countAll(dices);
		for (int i = 0; i < numbers.length; i++) 
			if (numbers[i] == 4) {
				flag = true;
				break;
			}
		if (flag == true)
			return sum(dices);
		else
			return 0;
	}
	
	public static int fullHouse(int[] dices) {
		boolean flag1 = false;
		boolean flag2 = false;
		int[] numbers = countAll(dices);
		for (int i = 0; i < numbers.length; i++) {
			if (numbers[i] == 2)  
				flag1 = true;
			if (numbers[i] == 3)
				flag2 = true;
		}
		if (flag1 == true && flag2 == true)
			return 25;
		else
			return 0;
	}
	
	public static int smallStraight(int[] dices) {
		int number1 = countOnes(dices);
		int number2 = countTwos(dices);
		int number3 = countThrees(dices);
		int number4 = countFours(dices);
		int number5 = countFives(dices);
		int number6 = countSixes(dices);
		if (number1 >= 1 && number2 >= 1 && number3 >= 1 && number4 >=1)
			return 30;
		if (number2 >= 1 && number3 >= 1 && number4 >= 1 && number5 >=1)
			return 30;
		if (number3 >= 1 && number4 >= 1 && number5 >= 1 && number6 >=1)
			return 30;
		return 0;
	}
	
	public static int largeStraight(int[] dices) {
		int number1 = countOnes(dices);
		int number2 = countTwos(dices);
		int number3 = countThrees(dices);
		int number4 = countFours(dices);
		int number5 = countFives(dices);
		int number6 = countSixes(dices);
		if (number1 >= 1 && number2 >= 1 && number3 >= 1 && number4 >=1 && number5 >= 1)
			return 40;
		if (number2 >= 1 && number3 >= 1 && number4 >= 1 && number5 >=1 && number6 >= 1)
			return 40;
		return 0;
	}
	
	public static int chance(int[] dices) {
		return sum(dices);
	}
	
	public static int yahtzee(int[] dices) {
		boolean flag = false;
		int[] numbers = countAll(dices);
		for (int i = 0; i < numbers.length; i++) 
			if (numbers[i] == 5) {
				flag = true;
				break;
			}
		if (flag == true)
			return 50;
		else
			return 0;
	}
	
	public static void setOnes(int[] dices, Label label) {
		int number = ones(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setTwos(int[] dices, Label label) {
		int number = twos(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setThrees(int[] dices, Label label) {
		int number = threes(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setFours(int[] dices, Label label) {
		int number = fours(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setFives(int[] dices, Label label) {
		int number = fives(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setSixes(int[] dices, Label label) {
		int number = sixes(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setSum(ArrayList<Label> labels, Label label) {
		int number = 0;
		for (int i = 0; i < labels.size(); i++) {
			int a = toInteger(labels.get(i).getText());
			number += a;
		}
		String s = Integer.toString(number);
		label.setText(s);
		label.setTextFill(Color.BLACK);
	}
	
	public static void setBonus(Label sum, Label label) {
		int a = toInteger(sum.getText());
		String s = Integer.toString(0);
		if (a >= 63) 
			s = Integer.toString(35);
		label.setText(s);
		label.setTextFill(Color.BLACK);
	}
	
	public static void setThreeOfAKind(int[] dices, Label label) {
		int number = threeOfAKind(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setFourOfAKind(int[] dices, Label label) {
		int number = fourOfAKind(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setFullHouse(int[] dices, Label label) {
		int number = fullHouse(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setSmallStraight(int[] dices, Label label) {
		int number = smallStraight(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setLargeStraight(int[] dices, Label label) {
		int number = largeStraight(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setChance(int[] dices, Label label) {
		int number = chance(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setYahtzee(int[] dices, Label label) {
		int number = yahtzee(dices);
		String s = Integer.toString(number);
		label.setText(s);
		if (number == 0)
			label.setTextFill(Color.GRAY);
		else
			label.setTextFill(Color.RED);
	}
	
	public static void setTotal(ArrayList<Label> labels, Label label) {
		int number = 0;
		for (int i = 0; i < labels.size(); i++) {
			int a = toInteger(labels.get(i).getText());
			number += a;
		}
		String s = Integer.toString(number);
		label.setText(s);
		label.setTextFill(Color.BLACK);
	}
}
