package hamid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;

public class Controller {

    @FXML private Text selectFile;
    @FXML private Text done;

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

    @FXML
    protected void handleFileChooserButtonAction(ActionEvent event) {
        done.setText("");
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Open File");
        File file = chooser.showDialog(new Stage());
        selectFile.setText(file.toString());
    }
    @FXML
    protected void handleOrganizeButton(ActionEvent event)throws IOException {
        selectFile.setDisable(true);
        done.setText("Processing");
        String current = selectFile.getText(); //"E:\\Download old";//System.getProperty("user.dir");
        final Path audioPath = Paths.get(current, "Music");
        final Path docsPath = Paths.get(current,"Documents");
        final Path imagesPath = Paths.get(current,"Pictures");
        final Path archivesPath = Paths.get(current,"Archives");
        final Path videosPath = Paths.get(current,"Videos");
        if(Files.notExists(audioPath))
            Files.createDirectories(audioPath);
        if(Files.notExists(docsPath))
            Files.createDirectories(docsPath);
        if(Files.notExists(imagesPath))
            Files.createDirectories(imagesPath);
        if(Files.notExists(archivesPath))
            Files.createDirectories(archivesPath);
        if(Files.notExists(videosPath))
            Files.createDirectories(videosPath);

        class MyFileVisitor extends SimpleFileVisitor<Path> {
            protected MyFileVisitor() {
                super();
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){

                String filename = file.getFileName().toString();
                int extensionpos = filename.lastIndexOf('.');
                String extension = filename.substring(extensionpos+1);
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
                        Files.move(file, Paths.get(videosPath.toString(), filename));
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
        System.out.println("Processing Files");
        Files.walkFileTree(Paths.get(current), EnumSet.noneOf(FileVisitOption.class),1,new MyFileVisitor());
        System.out.println("Done!");
        done.setText("Done!");
        selectFile.setDisable(false);
    }

    }
