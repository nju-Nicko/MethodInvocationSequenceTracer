package business_logic.ast;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ASTCreator {
	
	public CompilationUnit createAST(String filePath){
		byte[] input = null;  
        try {  
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));  
            input = new byte[bufferedInputStream.available()];  
            bufferedInputStream.read(input);  
            bufferedInputStream.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		
        ASTParser astParser = ASTParser.newParser(AST.JLS4);  
        astParser.setSource(new String(input).toCharArray());  
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);  
  
        CompilationUnit result = (CompilationUnit) (astParser.createAST(null));  
          
        return result;  
	}

}
