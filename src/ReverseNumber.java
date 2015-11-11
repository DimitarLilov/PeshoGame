/**
 * Created by User on 11.11.2015 ã..
 */
public class ReverseNumber {

    public static void main(String[] args) {

        int number = 7331024;
        int reversedNumber = 0, temp = 0;

        while(number > 0){
            temp = number % 10;
            reversedNumber = reversedNumber * 10 + temp;
            number = number / 10;

        }
        System.out.println("Reversed Number is: " + reversedNumber);
    }
}
