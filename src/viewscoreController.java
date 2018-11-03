import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class viewscoreController implements Initializable{
    @FXML
    private AnchorPane backpane;
    @FXML
    private Label nameLB;
    @FXML
    private Label surnameLB;
    @FXML
    private Label ageLB;
    @FXML
    private Label contactLB;
    @FXML
    private Label emailLB;
    @FXML
    private ScrollPane scrollPane;



    public void initialize(URL url, ResourceBundle rb) {
        nameLB.setText("Nitinon");
        surnameLB.setText("Penglao");
        contactLB.setText("0848841659");
        ageLB.setText("21");
        emailLB.setText("nitinon556@hotmail.com");

        GridPane gridpane = new GridPane();
        gridpane.setStyle("-fx-border-color:black;");
        gridpane.setMinSize(737, 588);
        gridpane.getColumnConstraints().add(new ColumnConstraints(0));
        scrollPane.setContent(gridpane);
        gridpane.add(createHeader(),0, 0);
        for(int i=1;i<100;i++){
            gridpane.add(createPane("eiei","Zaa"),0, i);
        }

    }
    public Pane createHeader (){
        Pane pane = new Pane();
        double wScore=scrollPane.getPrefWidth();
        double wLable=wScore/3;
        int headerSize = 20;

        pane.setMinSize(100, 25);

        Label topic_header = new Label("TopicHeader");
        topic_header.setAlignment(Pos.CENTER);
        topic_header.setStyle("-fx-border-color:black");
        topic_header.setFont(Font.font("Cambria", headerSize));
        topic_header.setMinHeight(25);
        topic_header.setMinWidth(wLable);
        topic_header.setMaxWidth(wLable);

        Label score_header = new Label("ScoreHeader");
        score_header.setAlignment(Pos.CENTER);
        score_header.setStyle("-fx-border-color:black");
        score_header.setFont(Font.font("Cambria",  headerSize));
        score_header.setMinHeight(25);
        score_header.setLayoutX(wLable);
        score_header.setMinWidth(wLable);
        score_header.setMaxWidth(wLable);

        Label maxscore_header = new Label("MaxScoreHeader");
        maxscore_header.setAlignment(Pos.CENTER);
        maxscore_header.setStyle("-fx-border-color:black");
        maxscore_header.setFont(Font.font("Cambria",  headerSize));
        maxscore_header.setMinHeight(25);
        maxscore_header.setLayoutX(wLable*2);
        maxscore_header.setMinWidth(258);
        maxscore_header.setMaxWidth(258);


        pane.getChildren().addAll(topic_header,score_header,maxscore_header);
        return pane;

    }


    public Pane createPane(String subject, String teacher ) {
        Pane pane = new Pane();
        double wScore=scrollPane.getPrefWidth();
        double wLable=wScore/3;
        int textSize = 18;

        pane.setMinSize(100, 25);

        Label topic_text = new Label("Topic");
        topic_text.setAlignment(Pos.CENTER);
        topic_text.setStyle("-fx-border-color:black");
        topic_text.setFont(Font.font("Cambria", textSize));
        topic_text.setMinHeight(25);
        topic_text.setMinWidth(wLable);
        topic_text.setMaxWidth(wLable);

        Label score_text = new Label("Score");
        score_text.setAlignment(Pos.CENTER);
        score_text.setStyle("-fx-border-color:black");
        score_text.setFont(Font.font("Cambria", textSize));
        score_text.setMinHeight(25);
        score_text.setLayoutX(wLable);
        score_text.setMinWidth(wLable);
        score_text.setMaxWidth(wLable);

        Label maxscore_text = new Label("Max Score");
        maxscore_text.setAlignment(Pos.CENTER);
        maxscore_text.setStyle("-fx-border-color:black");
        maxscore_text.setFont(Font.font("Cambria", textSize));
        maxscore_text.setMinHeight(25);
        maxscore_text.setLayoutX(wLable*2);
        maxscore_text.setMinWidth(258);
        maxscore_text.setMaxWidth(258);



        pane.getChildren().addAll(topic_text,score_text,maxscore_text);

        return pane;
    }

    @FXML
    public void jumpEnroll()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("enroll.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        enrollController controller = fxmlLoader.<enrollController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    @FXML
    public void jumpChange()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("changeCourse.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        changeCourseController controller = fxmlLoader.<changeCourseController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    @FXML
    public void jumpDrop()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("drop.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        dropController controller = fxmlLoader.<dropController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    @FXML
    public void jumpView()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewscore.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        viewscoreController controller = fxmlLoader.<viewscoreController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    @FXML
    public void jumpLogout() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }


}