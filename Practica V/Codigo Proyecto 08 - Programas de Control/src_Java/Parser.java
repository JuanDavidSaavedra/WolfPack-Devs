
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Parser {
	private File file;
	private Scanner scanner;
	private String currentCommand;
	
	public Parser(File file, FileInputStream fiStream) {
		this.file=file;
		this.scanner = new Scanner(fiStream);
	}
	
	public boolean hasMoreCommands() {
		if(this.scanner.hasNextLine()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void advance() {
		if(hasMoreCommands()) {
			do{
				this.currentCommand = this.scanner.nextLine();
			}while(!getCommand());
		}else {
			this.scanner.close();
		}
	}
	
	private boolean getCommand(){
		String stringLine = this.currentCommand;
		if(stringLine.equals("")) {
			return false;
		}
		String head = stringLine.substring(0, 2);  // get the first two characters
		String headChar = stringLine.trim().substring(0,2); // get the first two characters
		if(head.equals("//") || headChar.equals("//")) {
			return false;
		}
		if(stringLine.contains("//")) {
			String subStr = stringLine.substring(0, stringLine.indexOf("//"));
			this.currentCommand = subStr.trim();
			return true;
		}
		this.currentCommand = stringLine.trim();
		return true;
	}
	
	public CommandType commandType() {
		String commandLabel = "";
		if(this.currentCommand.contains(" ")) {
			commandLabel = this.currentCommand.substring(0, this.currentCommand.indexOf(" "));
		}else {
			commandLabel = this.currentCommand;
		}
		 
		switch (commandLabel) {
		case "add": case "sub": case "neg":
		case "eq":  case "gt":  case "lt":
		case "and": case "or":  case "not":
			return CommandType.C_ARITHMETIC;
		case "pop":
			return CommandType.C_POP;
		case "push":
			return CommandType.C_PUSH;
		case "label":
			return CommandType.C_LABEL;
		case "goto":
			return CommandType.C_GOTO;
		case "if-goto":
			return CommandType.C_IF;
		case "function":
			return CommandType.C_FUNCTION;
		case "call":
			return CommandType.C_CALL;
		case "return":
			return CommandType.C_RETURN;
		default:
			break;
		}
		return null;
	}
	
	public String args1() {
//		if(commandType().equals(CommandType.C_RETURN)) {
//			return null;
//		}
		if(commandType().equals(CommandType.C_ARITHMETIC)) {
			return this.currentCommand;
		}
		String args1 = "";
		if(commandType().equals(CommandType.C_LABEL)||
		   commandType().equals(CommandType.C_GOTO)||
		   commandType().equals(CommandType.C_IF)) {
			args1 = this.currentCommand.substring(this.currentCommand.indexOf(" ")+1, currentCommand.length());
		}else{
			args1 = this.currentCommand.substring(this.currentCommand.indexOf(" ")+1, this.currentCommand.lastIndexOf(" "));
		}
		return args1;
	}
	
	public String args2() {
		return this.currentCommand.substring(this.currentCommand.lastIndexOf(" ")+1, this.currentCommand.length());
	}

	public File getFile() {
		return file;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public String getCurrentCommand() {
		return currentCommand;
	}
}