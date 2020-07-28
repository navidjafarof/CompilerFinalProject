import java.io.FileReader;
import java.io.IOException;
import java.io.FileOutputStream;

import Lexical.LexicalAnalyzer;
import Semantic.CodeGenerator;
import Semantic.AST.AST;
import Syntax.Parser;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class Main {
    public static void main(String[] args) throws IOException {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(new FileReader("src/Code.txt"));
        CodeGenerator codeGenerator = new CodeGenerator(lexicalAnalyzer);
        Parser parser = new Parser(lexicalAnalyzer, codeGenerator, "src/syntax/table.npt", true);
        AST result;
        try {
            parser.parse();
            result = codeGenerator.getResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER, "Test",
                null, "java/lang/Object", null);
        MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_STATIC | Opcodes.ACC_PUBLIC,
                "main", "([Ljava/lang/String;)V", null, null);
        methodVisitor.visitCode();
        result.codegen(classWriter, methodVisitor);
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();

        // Generate class file
        try (FileOutputStream fos = new FileOutputStream("Test.class")) {
            fos.write(classWriter.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Code compiled successfully");

//        Symbol nt = lexicalAnalyzer.next_token();
//        while (nt.getToken()!="$"){
//            System.out.println(nt.getToken() +" "+nt.getValue());
//            nt = lexicalAnalyzer.next_token();
//        }


    }
}



