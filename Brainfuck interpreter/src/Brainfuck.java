import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Brainfuck {
	public static void main(String[] args) {
		byte[] memory = new byte[256];
		byte pointer = 0;
		int instruction = 0;
		String input = "";
		char[] decode;
		Scanner in = new Scanner(System.in);
		String path;
		System.out.println("Which file to interpret?");
		path = in.next();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			while(line != null)
			{
				input = input + line;
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		decode = input.toCharArray();
		while(instruction < decode.length){		
			switch(decode[instruction]){
			case '+' :
				memory[(int)pointer&0xFF] ++;
				instruction ++;
				break;
			case '-' :
				memory[(int)pointer&0xFF] --;
				instruction++;
				break;
			case '>' :
				pointer ++;
				instruction ++;
				break;
			case '<' :
				pointer --;
				instruction ++;
				break;
			case '[' :
				if(memory[(int)pointer&0xFF] == 0){
					int count = 0;
					while(instruction < decode.length){
						instruction ++;
						if(decode[instruction] == '[') count ++;
						else if(decode[instruction] == ']'){
							if(count == 0) break;
							else count--;
						}
					}
				}
				else instruction ++;
				break;
			case ']' :
				if(memory[(int)pointer&0xFF] != 0){
					int count = 0;
					while(instruction > 0){
						instruction --;
						if(decode[instruction] == ']') count ++;
						else if(decode[instruction] == '['){
							if(count == 0) break;
							else count--;
						}
					}
				}
				else instruction ++;
				break;
			case '.' :
				System.out.print((char)((int)memory[(int)pointer&0xFF]&0xFF));
				instruction ++;
				break;
			case ',' :
				memory[(int)pointer&0xFF] = (byte) in.nextLine().charAt(0);
				instruction++;
				break;
			default :
				instruction++;
			}
		}
		in.close();
	}
}
