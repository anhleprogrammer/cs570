package main.java.HW1;
import java.util.*;
public class BinaryNumber {

    private int[] data;
    private boolean overflow;

    // Create a binary number with only 0 as values based on the input length
    public BinaryNumber(int length) {
        data = new int[length];
        Arrays.fill(data, 0);
    }

    // Create binary number based on input string
    public BinaryNumber(String str) {
        //Create an array with length equals the length of input string
        data = new int[str.length()];
        //Convert character to number
        int i = 0;
        for(char c: str.toCharArray()) {
            data[i] = Character.getNumericValue(c);
            i++;
        }
     }

     //Return the length of the binary number
    public int getLength() {
        return data.length;
    }

    public int getDigit(int index) {
        // method body
        if(index < 0 || index >= data.length) throw new IndexOutOfBoundsException("Error: index out of bounds");
        return data[index];
    }



    public void shiftR(int amount) {
        // resize length of array everytime we shift
        int[] newArr = new int[amount + data.length];
        System.arraycopy(data,0, newArr, amount, data.length);
        data = newArr;
    }

    public void add(BinaryNumber aBinaryNumber) {
        // method body
        if(aBinaryNumber.getLength() != data.length) {
            System.out.println("Length of input binary do not coincide with this binary");
        } else {

            ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
            int carryOver = 0;
            for(int i = getLength() - 1; i>= 0; i--) {
                if(this.getDigit(i) + aBinaryNumber.getDigit(i) >= 2) {
                    deque.addFirst(carryOver);
                    carryOver = 1;
                } else {
                    if(carryOver == 1) {
                        if(this.getDigit(i) + aBinaryNumber.getDigit(i) + carryOver < 2) {
                            deque.addFirst(this.getDigit(i) + aBinaryNumber.getDigit(i) + carryOver);
                            carryOver = 0;
                        } else {
                            deque.addFirst(0);
                            carryOver = 1;
                        }
                    } else {
                        deque.addFirst(this.getDigit(i) + aBinaryNumber.getDigit(i));
                    }
                }
                if(i == 0 && carryOver == 1) deque.addFirst(1);
            }
            int[] result = deque.stream().mapToInt(e -> e.intValue()).toArray();
            data = result;
        }
    }

    //Return the Binary number as a string
    public String toString() {
        //The decimal number is made of the value inside the array data
        StringBuilder sb = new StringBuilder();
        for( int num: data) {
            sb.append(String.valueOf(num));
        }
        return sb.toString();
    }


    public int toDecimal() {
        //given an array of integer
        //multiply the last element of array to x with starting x = 1
        //shift to the next element( left or current one) and multiple to x * 2
        int result = 0;
        int x = 1;
        for(int i = data.length - 1; i >= 0; i--) {
            result += data[i] * x;
            x *= 2;
        }
        return result;
    }
    public void clearOverflow() {
        // method body
        overflow = false;
    }
    public static void main(String[] args) {

        BinaryNumber bn1 = new BinaryNumber(7);
        BinaryNumber bn2 = new BinaryNumber("1011011");
        BinaryNumber bn3 = new BinaryNumber("1111111");



        BinaryNumber bn4 = new BinaryNumber("1101");
        System.out.println(bn4.toDecimal());


        System.out.println("Binary 1: " + bn1.toString());
        System.out.println("Binary 2: " + bn2.toString());
        System.out.println("Binary 3: " + bn3.toString());


        bn1.add(bn2);
        System.out.println("Binary 1 + Binary 2: " +bn1.toString());

        bn2.add(bn3);

        System.out.println("Binary 2 + Binary 3: " + bn2.toString());

    }
}