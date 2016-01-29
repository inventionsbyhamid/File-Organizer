package hamid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML private CheckBox checkBoxCustom;
    @FXML private TextField customFolderName;
    @FXML private TextField customExtension;
    @FXML private Label customFolderNameLabel;
    @FXML private Label customExtensionLabel;

    static private boolean DEFAULT = true;

    public void initialize(){
        selectFile.setText(System.getProperty("user.dir"));

    }

    @FXML
    protected void handleFileChooserButtonAction(ActionEvent event) {
        done.setText("");
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setInitialDirectory(new File(selectFile.getText()));
        chooser.setTitle("Select Folder");
        File file = chooser.showDialog(new Stage());
        selectFile.setText(file.toString());
    }
    @FXML protected void handleCustomCheckBoxAction(ActionEvent event){

        if (checkBoxCustom.isSelected()) {
            customFolderName.setVisible(true);
            customExtension.setVisible(true);
            customExtensionLabel.setVisible(true);
            customFolderNameLabel.setVisible(true);
            DEFAULT = false;

        } else {
            customFolderName.setVisible(false);
            customExtension.setVisible(false);
            customExtensionLabel.setVisible(false);
            customFolderNameLabel.setVisible(false);
            DEFAULT=true;
        }
        done.setText("");

    }
    @FXML
    protected void handleOrganizeButton(ActionEvent event)throws IOException {
        selectFile.setDisable(true);

        String current = selectFile.getText(); //"E:\\Download old";//System.getProperty("user.dir");

        System.out.println("Processing Files");
        if(!DEFAULT && !customExtension.getText().equals("") && !customFolderName.getText().equals(""))
        {   done.setText("Processing..");
            Files.walkFileTree(Paths.get(current), EnumSet.noneOf(FileVisitOption.class), 1, new CustomFileVisitor(current, customExtension.getText().toLowerCase().split(","),customFolderName.getText()));
            done.setText("Done!");
        }
        else
            if(!DEFAULT && (customExtension.getText().equals("")||customFolderName.getText().equals("")))
                done.setText("Folder Name or Extension is empty.\nUncheck Custom option for default.");
        else
        {   done.setText("Processing..");
            Files.walkFileTree(Paths.get(current), EnumSet.noneOf(FileVisitOption.class), 1, new DefaultFileVisitor(current));
            done.setText("Done!");
        }
        selectFile.setDisable(false);
    }

    }
