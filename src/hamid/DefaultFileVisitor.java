package hamid;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Hamid on 1/29/2016.
 */

class DefaultFileVisitor extends SimpleFileVisitor<Path> {

    static final String audio_formats[] = {"mp3","aac","flac","ogg","wma","m4a","aiff"};
    static final String image_formats[] = {"png","jpeg","jpg","gif","bmp","svg","webp"};
    static final String doc_formats[]   = {"pdf","doc","docx","xls","xlsv","ppt","pptx","ppsx"};
    static final String archive_formats[] = {"zip","rar","7z","tar","gz","bz2","iso","dmg"};
    static final String video_formats[] = {"flv","ogg","ogv","avi","mov","qt","wmv","mp4","mpg","mpeg","3gp","mkv"};

    static final HashSet<String> images = new HashSet<String>(Arrays.asList(image_formats));
    static final HashSet<String> audio = new HashSet<String>(Arrays.asList(audio_formats));
    static final HashSet<String> docs = new HashSet<String>(Arrays.asList(doc_formats));
    static final HashSet<String> archives = new HashSet<String>(Arrays.asList(archive_formats));
    static final HashSet<String> videos = new HashSet<String>(Arrays.asList(video_formats));
    final Path audioPath,docsPath,imagesPath,archivesPath,videosPath;
    String source=null;
    protected DefaultFileVisitor(String source) {
        super();
        this.source = source;
        audioPath = Paths.get(source, "Music");
        docsPath = Paths.get(source,"Documents");
        imagesPath = Paths.get(source,"Pictures");
        archivesPath = Paths.get(source,"Archives");
        videosPath = Paths.get(source,"Videos");
        createDirs();
    }
    private void createDirs(){
        try {
            if (Files.notExists(audioPath))
                Files.createDirectories(audioPath);
            if (Files.notExists(docsPath))
                Files.createDirectories(docsPath);
            if (Files.notExists(imagesPath))
                Files.createDirectories(imagesPath);
            if (Files.notExists(archivesPath))
                Files.createDirectories(archivesPath);
            if (Files.notExists(videosPath))
                Files.createDirectories(videosPath);
        }
        catch(IOException e)
        {
            System.out.println("Directory cannot be created "+e.getMessage());
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){


        String filename = file.getFileName().toString();
        int extensionpos = filename.lastIndexOf('.');
        String extension = filename.substring(extensionpos+1).toLowerCase();
        try {
            if (images.contains(extension))
            {
                Files.move(file, Paths.get(imagesPath.toString(), filename));
            }
            else if (audio.contains(extension))
            {
                Files.move(file, Paths.get(audioPath.toString(), filename));
            }
            else if (docs.contains(extension))
            {
                Files.move(file, Paths.get(docsPath.toString(), filename));
            }
            else if (archives.contains(extension))
            {
                Files.move(file, Paths.get(archivesPath.toString(), filename));
            }
            else if (videos.contains(extension))
            {
                Files.move(file, Paths.get(videosPath.toString(), filename),StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch(IOException e){
            System.out.println("File not moved "+ e.getMessage());
            System.out.println("File not moving cause "+ e.getCause());
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



    
