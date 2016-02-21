import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Brainfuck 
{



	public static void main(String[] args) 
	{

		byte[] memory = new byte[256];
		int[] jumps = new int[2048];
		byte pointer = 0;
		int instruction = 0;
		String input = "";
		char[] decode;
		int jumpback = 0;
		int jumpforward = 0;

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

		while(instruction < decode.length)
		{		
			switch(decode[instruction])
			{
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
				if(jumps[instruction] == 0)
				{
					jumpback = instruction;
					for(int x = instruction + 1; x < decode.length; x ++)
					{
						int nest = 0;
						if(decode[x] == '[')
						{
							nest ++;
							x ++;
						}
						else if(decode[x] == ']')
						{
							if(nest == 0)
							{
								jumps[x] = jumpback;							
								jumpforward = x;
								break;
							}
							else
							{
								nest --;
								x ++;
							}
						}
						else x ++;
					}
					jumps[instruction] = jumpforward;
				}				
				else
				{
					if(memory[(int)pointer&0xFF] == 0) instruction = jumps[instruction];
					else instruction ++;
				}
				break;
			case ']' :
				if(memory[(int)pointer&0xFF] != 0) instruction = jumps[instruction];
				else instruction ++;
				break;
			case '.' :
				System.out.println(memory[(int)pointer&0xFF]);
				instruction ++;
				break;
			case ',' :
				memory[(int)pointer&0xFF] = (byte) in.nextLine().charAt(0);
				break;
			default :
				instruction++;
			}

		}
		
		in.close();

	}

}
