package hamid;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Hamid on 1/29/2016.
 */

class CustomFileVisitor extends SimpleFileVisitor<Path> {


    private String extensions[] = null;
    private Path newFilePath;
    protected CustomFileVisitor(String source,String[] extensions,String folderName) {
        super();
        newFilePath = Paths.get(source,folderName);
        this.extensions = extensions;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)throws IOException{


        String filename = file.getFileName().toString();
        int extensionpos = filename.lastIndexOf('.');
        String extension = filename.substring(extensionpos+1).toLowerCase();
        if(Files.notExists(newFilePath))
            Files.createDirectories(newFilePath);
        if(extensions==null)
            System.out.println("No extensions");
        for(String ex: extensions) {
            if(ex.equals(extension))
                try {
                    Files.move(file, newFilePath.resolve(filename));
                }
                catch(IOException e) {
                    System.out.println("File not moved "+ file);
                    System.out.println("Error "+ e.getMessage());
                    System.out.println("Cause of not moving "+ e.getCause());
                }
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("Directory visiting " + dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("File visit failed" + file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("Directory finish " + dir);
        return FileVisitResult.CONTINUE;
    }
}




