import classss.Student;
import classss.Subject;
import component.passwordDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class enrollController implements Initializable {
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
    private Label telLB;
    @FXML
    private Label yearLB;
    @FXML
    private Label facultyLB;
    //------------------------------------------------------------------------
    @FXML
    private ScrollPane scrollPane1;
    @FXML
    private ScrollPane scrollPane2;
    //    get list of all subject
//    get current id-------------------------------------------------
    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Student currentStudent = getObjStudent(id);
    List<Subject> listAllSubject = getAllSubject();
    Boolean enable;

    public void initialize(URL url, ResourceBundle rb) {
        updateScreen();
        enable = userPreferences.getBoolean("Enable", true);
    }

    public void updateScreen() {
        currentStudent = getObjStudent(id);
        System.out.println("Updateddddddddddddddddddddddddddddddddddddddd");
        //    show info of current user---------------------------------------
        nameLB.setText(currentStudent.getName());
        surnameLB.setText(currentStudent.getSurname());
        contactLB.setText(currentStudent.getPhonenumber());
        ageLB.setText(currentStudent.getBirthday());
        emailLB.setText(currentStudent.getEmail());
        telLB.setText(currentStudent.getPhonenumber());
        yearLB.setText(Integer.toString(currentStudent.getYear_of_study()));
        facultyLB.setText(currentStudent.getFaculty());

        GridPane gridpane = new GridPane();
        gridpane.setMinSize(scrollPane1.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        scrollPane1.setContent(gridpane);
        gridpane.add(createHeader2(), 1, 1);
        int i = 0;
        for (Subject a : currentStudent.getSubject()) {
            i++;
            gridpane.add(createPane2(i, a), 1, i + 2);
        }
        gridpane = new GridPane();
        gridpane.setMinSize(scrollPane2.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        scrollPane2.setContent(gridpane);
        gridpane.add(createHeader(), 1, 1);
        i = 0;
        for (Subject a : listAllSubject) {
            i++;
            if (!findSubject(a) && a.getApprove()) {
                gridpane.add(createPane(i, a), 1, i + 2);
            }
        }
    }

    public Boolean findSubject(Subject sub) {
        for (Subject a : currentStudent.getSubject()) {
            if (a.getId_sub() == sub.getId_sub())
                return true;
            Boolean midtermDup = a.getMidtermExam().equals(sub.getMidtermExam()) && a.getMidtermTime().equals(sub.getMidtermTime());
            Boolean finalDup = a.getFinalExam().equals(sub.getFinalExam()) && a.getFinalTime().equals(sub.getFinalTime());
            Boolean dayDup = a.getDay().equals(sub.getDay()) && a.getTime().equals(sub.getTime());
            if (midtermDup || finalDup || dayDup)
                return true;
        }
        return false;
    }

    public Boolean duplicated(Subject sub) {
        for (Subject a : currentStudent.getSubject()) {
            if (a.getId_sub() == sub.getId_sub())
                return true;
        }
        return false;
    }

    public Pane createHeader() {
        Pane pane = new Pane();
        int numCol=5;
        double wScore = scrollPane2.getPrefWidth();
        double wLable = wScore / 5;
        pane.setMinSize(100, 25);
        String[] topic = {"ID","Subject","Student","Description","Enroll"};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15;-fx-background-color: #ffd410; ");
            pane.getChildren().add(topic_text);
        }
        return pane;
    }


    public Pane createPane(int id, Subject subject) {
        Pane pane = new Pane();
        int numCol=5;
        double wScore = scrollPane1.getPrefWidth();
        double wLable = wScore / 5;

        pane.setMinSize(100, 25);
        String[] topic = {subject.getId_sub() + "",subject.getName(),subject.getStudentNum() + "/" + subject.getNo_student(),"",""};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15;-fx-background-color: white; ");
            pane.getChildren().add(topic_text);
        }

        Button btn1 = createDesBT("description", subject.getId_sub());
        btn1.setStyle("-fx-alignment:center; -fx-font-size: 10");
        btn1.setLayoutX(wLable * 3 + 20);
        btn1.setMinSize(50, 10);

        Button btn2 = createEnrollBT("enroll", subject.getId_sub());
        btn2.setStyle("-fx-alignment:center; -fx-font-size: 10");
        btn2.setLayoutX(wLable * 4 + 25);
        btn2.setMinSize(50, 10);
        pane.getChildren().addAll(btn1, btn2);

        return pane;
    }

    public Pane createHeader2() {
        Pane pane = new Pane();
        int numCol=4;
        double wScore = scrollPane2.getPrefWidth();
        double wLable = wScore / 4;
        pane.setMinSize(100, 25);
        String[] topic = {"ID","Subject","Teacher","Description"};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15;-fx-background-color: #ffd410; ");
            pane.getChildren().add(topic_text);
        }
        return pane;
    }


    public Pane createPane2(int id, Subject subject) {
        Pane pane = new Pane();
        int numCol=4;
        double wScore = scrollPane1.getPrefWidth();
        double wLable = wScore / 4;

        pane.setMinSize(100, 25);
        String[] topic = {subject.getId_sub() + "",subject.getName(),subject.getTeacher().getName() + " " + subject.getTeacher().getSurname(),""};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15;-fx-background-color: white; ");
            pane.getChildren().add(topic_text);
        }

        Button btn1 = createDesBT("description", subject.getId_sub());
        btn1.setLayoutX(wLable * 3 + 20);

        pane.getChildren().addAll(btn1);

        return pane;
    }


    public Label createLable(String txt, double height, double width, double pos) {
        Label label = new Label(txt);
        label.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:20");
        label.setMinHeight(height);
        label.setMinWidth(width);
        label.setMaxWidth(width);
        label.setLayoutX(pos);
        return label;
    }

    public Button createDesBT(String txt, long subject) {
        Button btn = new Button(txt);

        btn.setOnAction(event -> {
            createDialog(subject);
        });
        return btn;
    }

    public Button createEnrollBT(String txt, long subject) {
        Button btn = new Button(txt);
        btn.setOnAction(event -> {
            if (enable)
                createEnrollDialog(subject);
            else popUp(false, "Disable", "Can not enroll this time");
        });
        return btn;
    }

    public void createDialog(long id) {
        Subject foundedSubject = getSubject(id);
        String info = "Name: " + foundedSubject.getName() + "\n" +
                "Teacher: " + foundedSubject.getTeacher() + "\n" +
                "Description: " + foundedSubject.getDiscription() + "\n" +
                "--------------------------------------------------\n"+
                "Teaching day  : " + foundedSubject.getDay()+"---"+foundedSubject.getTime()+"\n"+
                "Midterm/time :" + foundedSubject.getMidtermExam() + "---" + foundedSubject.getMidtermTime()+"\n"+
                "Final/time       :" + foundedSubject.getFinalExam() + "---" + foundedSubject.getFinalTime();
                popUp(true, "Course Info", info);
    }

    public void createEnrollDialog(long id) {
        passwordDialog pd = new passwordDialog();
        Optional<String> result = pd.showAndWait();
        result.ifPresent(password -> {
            if (password.equals(currentStudent.getPassword())) {
                enroll(id);
            } else {
                System.out.println("Error");
            }
        });
    }

    public void enroll(long id) {
        System.out.println(id);
        Subject subjectSeleted = getSubject(id);
        Boolean dup = false;
        for (Subject temp : currentStudent.getSubject()) {
            if (temp.getDay().equals(subjectSeleted.getDay()) && temp.getTime().equals(subjectSeleted.getTime())) {
                dup = true;
            }
        }
        if (dup) popUp(false, "Dupilcated", "You have subject in this time");
        else {
            if (subjectSeleted.subjectIsFull()) {
                popUp(false, "Full", "course full");
            } else {
                enrollCourse((int) currentStudent.getId(), id);
                popUp(true, "Success", "enroll course success");
            }
        }
        updateScreen();
    }

    public void popUp(Boolean success, String header, String txt) {
        if (success == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(header);
            alert.setHeaderText(null);
            alert.setContentText(txt);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(header);
            alert.setHeaderText(null);
            alert.setContentText(txt);
            alert.showAndWait();
        }
    }

    //    ======================================DB==============================================================
    public static classss.Student getObjStudent(long id_stu) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Student c Where c.id =" + id_stu + "";
        TypedQuery<Student> query1 = em.createQuery(sql1, classss.Student.class);
        List<Student> results1 = query1.getResultList();
        if (results1.size() == 0) {
            return null;
        } else {
            return results1.get(0);
        }
    }

    public static List<Subject> getAllSubject() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Subject c ";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();
        return results2;
    }

    public static Subject getSubject(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();
        return results2.get(0);
    }

    public static void enrollCourse(int id_stu, long id_sub) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        String sql1 = "SELECT c FROM Student c Where c.id =" + id_stu + "";
        TypedQuery<classss.Student> query1 = em.createQuery(sql1, classss.Student.class);
        List<classss.Student> results1 = query1.getResultList();

        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id_sub + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();

        em.getTransaction().begin();
        results1.get(0).addSubject(results2.get(0));
        //em.persist(results1.get(0));
        //em.persist(results2.get(0));
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    //    jump=======================================================================================
    public void jumpEnroll() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/enroll.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        enrollController controller = fxmlLoader.<enrollController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpChange() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/changeCourse.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        changeCourseController controller = fxmlLoader.<changeCourseController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpDrop() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/drop.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        dropController controller = fxmlLoader.<dropController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/viewscore.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        viewscoreController controller = fxmlLoader.<viewscoreController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpChangePass() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/changePass.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        changePassController controller = fxmlLoader.<changePassController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpEditProfile() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/editProfile.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        ediProfileController controller = fxmlLoader.<ediProfileController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpAnnounce() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/announcement.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        announcementController controller = fxmlLoader.<announcementController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpSchedule() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/schedule.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        scheduleController controller = fxmlLoader.<scheduleController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpLogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }


}
