package com.example.dictionary;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloController {

    @FXML
    GridPane gridPane;

    @FXML
    ImageView british;
    @FXML
    ImageView spanish;
    @FXML
    ImageView german;
    @FXML
    Label label;
    @FXML
    TextField textField;
    @FXML
    ImageView french;
    @FXML
    ImageView italian;
    @FXML
    ImageView british1;
    @FXML
    ImageView french1;
    @FXML
    ImageView polish;
    @FXML
    Label swapLabel;

    Map<ImageView, String[]> map = new HashMap<>();

    boolean changeLeft = true;

    public void initialize() throws IOException {

        moveLeft();
        moveRight();

        map.put(british, new String[]{"britishFlag.png", "englishDictionary.rtf"});
        map.put(french, new String[]{"frenchFlag.png", "frenchDictionary.rtf"});
        map.put(german, new String[]{"germanFlag.png", "germanDictionary.rtf"});
        map.put(spanish, new String[]{"spanishFlag.jpg", "spanishDictionary.rtf"});
        map.put(italian, new String[]{"italianFlag.jpg", "italianDictionary.rtf"});
        map.put(polish, new String[]{"polishFlag.jpeg", "polishDictionary.rtf"});
        map.put(british1, new String[]{"britishFlag.png", "englishDictionary.rtf"});
        map.put(french1, new String[]{"frenchFlag.png", "frenchDictionary.rtf"});

        DictionaryOperations.loadEnglishToPolish("englishDictionary.rtf","frenchDictionary.rtf");
        showImage(british, french, german, german, spanish, spanish);
        showImage(italian, polish, british1, british, french1, french);
        french.setOpacity(0.75);
        french1.setOpacity(0.5);
        british1.setOpacity(0.5);
        french1.setDisable(true);
        british1.setDisable(true);

        respondUi(swapLabel);
        gridPane.getChildren().forEach(item -> {
            respondUi(item);

            item.setOnMouseClicked(mouseEvent -> {
                if(changeLeft) {
                    textField.setText("");
                    label.setText("Translation");
                    british.setImage(new Image(new File(map.get(item)[0]).toURI().toString()));
                    map.replace(british, map.get(item));
                } else {
                    french.setImage(new Image(new File(map.get(item)[0]).toURI().toString()));
                    map.replace(french, map.get(item));

                }


                try {

                    DictionaryOperations.setEnglishToPolish(DictionaryOperations.loadEnglishToPolish(map.get(british)[1], map.get(french)[1]));
                    translate();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                gridPane.getChildren().forEach(item1 -> {
                    item1.setDisable(false);
                    item1.setOpacity(1);
                });
                item.setDisable(true);
                item.setOpacity(0.5);
                block();
            });
        });
    }

    private void respondUi(Node swapLabel) {
        swapLabel.setOnMouseEntered(mouseEvent -> {
            ScaleTransition t = new ScaleTransition(Duration.seconds(0.3), swapLabel);


            t.setToX(1.2);
            t.setToY(1.2);
            t.setToZ(1.2);

            t.play();
        });

        swapLabel.setOnMouseExited(mouseEvent -> {
            ScaleTransition t = new ScaleTransition(Duration.seconds(0.3), swapLabel);
            t.setToX(1);
            t.setToY(1);
            t.setToZ(1);

            t.play();
        });
    }

    private void showImage(ImageView italian, ImageView polish, ImageView british1, ImageView british, ImageView french1, ImageView french) {
        italian.setImage(new Image(new File(map.get(italian)[0]).toURI().toString()));
        polish.setImage(new Image(new File(map.get(polish)[0]).toURI().toString()));
        british1.setImage(new Image(new File(map.get(british)[0]).toURI().toString()));
        french1.setImage(new Image(new File(map.get(french)[0]).toURI().toString()));
    }

    public void translate() {

        label.setText(DictionaryOperations.getEnglishToPolish().getOrDefault(textField.getText().toUpperCase(), "Translation"));
    }

    public void delete(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
            label.setText("Translation");
            textField.setText("");
        }
    }


    private void move(ImageView british) {
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(Timeline.INDEFINITE);


        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), actionEvent -> {
            ScaleTransition t = new ScaleTransition(Duration.seconds(1), british);
            t.setToX(1.05);
            t.setToY(1.05);
            t.setToZ(1.05);

            ScaleTransition s = new ScaleTransition(Duration.seconds(1), british);
            s.setToX(0.95);
            s.setToY(0.95);
            s.setToZ(0.95);

            SequentialTransition g = new SequentialTransition(t,s);
            g.play();
        });

        timeLine.getKeyFrames().add(keyFrame);
        timeLine.play();
    }

    public void moveRight(){
        move(french);
    }

    public void moveLeft() {
        move(british);
    }

    public void setLeft() {
        british.setOpacity(1);
        french.setOpacity(0.75);
        british.setDisable(true);
        french.setDisable(false);
        changeLeft = true;

        block();
    }

    public void setRight(){
        french.setOpacity(1);
        british.setOpacity(0.75);
        french.setDisable(true);
        british.setDisable(false);
        changeLeft = false;
        block();
    }

    public void block() {
        gridPane.getChildren().forEach(item -> {
            item.setDisable(false);
            item.setOpacity(1);
        });

        for(ImageView iv : map.keySet()) {
            if(iv.equals(french) || iv.equals(british)) {
                continue;
            }
            if(map.get(french)[0].equals(map.get(iv)[0]) || map.get(british)[0].equals(map.get(iv)[0])) {
                iv.setDisable(true);
                iv.setOpacity(0.5);
            }
        }

    }

    public void swap() throws IOException {
        label.setText("Translation");
        textField.setText("");
        british.setImage(new Image(new File(map.get(french)[0]).toURI().toString()));
        french.setImage(new Image(new File(map.get(british)[0]).toURI().toString()));

        String[] temp = map.get(british);
        map.put(british, map.get(french));
        map.put(french, temp);

        DictionaryOperations.setEnglishToPolish(DictionaryOperations.loadEnglishToPolish(map.get(british)[1], map.get(french)[1]));
    }
}
