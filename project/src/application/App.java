package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class App extends Application{
	private Stage stage;
	private TextFlow chat;
	private Label turn;
    
	private GridPane grid;
	private ArrayList<Label> scores;
	private ArrayList<Label> players;
	private ArrayList<Label> column1;
	private ArrayList<Label> column2;
	
	Random random = new Random();
	private ImageView diceImage1;
	private ImageView diceImage2;
	private ImageView diceImage3;
	private ImageView diceImage4;
	private ImageView diceImage5;
	private Button rollButton;
	private Dice dice1;
	private Dice dice2;
	private Dice dice3;
	private Dice dice4;
	private Dice dice5;
	private Dice[] dices;
	private boolean firstRoll = true;
	private int numberRoll = 0;
	
	private Client client;
	private String username;
	private String oponentsName;
	private boolean oponentConnected = false;
	private boolean enemyTurn = false;
	private boolean end = false;
	
	@Override
	public void start(Stage stage) {
		try {
			client = new Client(this);
			client.start();
			this.stage=stage;
			
			File file = new File("src/addings/picture.jpg");
			ImageView picture = new ImageView();
			picture.setImage(new Image(file.toURI().toString()));
			picture.setFitHeight(600);
	        picture.setFitWidth(300);
	        
	        HBox p = new HBox(15);
	        p.setMinSize(300, 600);
	        p.getChildren().addAll(picture);
			
			Group start=new Group();
			
			Button rules = new Button("RULES");
			rules.setTextFill(Color.BLACK);
			rules.setStyle("-fx-background-color: #85b093");
			rules.setMaxSize(100, 20);
			rules.setLayoutX(30);
			rules.setLayoutY(120);
			rules.setFont(Font.font("Ariel",FontWeight.BOLD, 13));
			
			
			Button leave = new Button("LEAVE");
			leave.setTextFill(Color.BLACK);
			leave.setStyle("-fx-background-color: #85b093");
			leave.setMaxSize(100,20);
			leave.setLayoutX(230);
			leave.setLayoutY(120);
			leave.setFont(Font.font("Ariel",FontWeight.BOLD, 13));
			
			
			Label label1= new Label("ENTER USERNAME:");
			label1.setLayoutX(100);
			label1.setLayoutY(340);
			label1.setTextFill(Color.rgb(255, 255, 255));
			label1.setFont(Font.font("Ariel", FontWeight.BOLD, 13));
			
			TextField userName = new TextField();
			userName.setMaxWidth(260);
			userName.setMaxHeight(40);
			userName.setLayoutX(85);
			userName.setLayoutY(370);
			userName.setStyle("-fx-background-color: #326d6c;  -fx-text-fill: #ffffff; -fx-font-weight: bold");
			userName.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,null)));
			
		
			Button play = new Button(" PLAY ");
			play.setTextFill(Color.BLACK);
			play.setStyle("-fx-background-color: #85b093");
			play.setMaxWidth(250);
			play.setMinHeight(30);
			play.setLayoutX(130);
			play.setLayoutY(410);
			play.setFont(Font.font("Ariel",FontWeight.BOLD, 13));
			
			Label error =new Label(" ENTER USERNAME! ");
			error.setTextFill(Color.rgb(255, 0, 0));
			error.setFont(Font.font("Ariel", FontWeight.BOLD, 13));
			error.setVisible(false);
			error.setLayoutX(100);
			error.setLayoutY(460);
			
			start.getChildren().addAll(rules, leave, label1, userName, play, error);
			
			HBox root = new HBox(15);
			root.getChildren().addAll(p, start);
			root.setStyle("-fx-background-color: #094152");
			
			Scene scene = new Scene(root,600,600);
			stage.setScene(scene);
			stage.setTitle("YAHTZEE"); 
			stage.setResizable(false);
			stage.centerOnScreen();
			stage.show();
			
			
			play.setOnAction(e-> {
				if(userName.getText().isEmpty()) {
					error.setVisible(true);
					return;
				}
				error.setText("");
				username = userName.getText();
				client.setUsername(userName.getText());
				client.sendUsername();
				
				VBox cet=makeChat();
				VBox desno=getGame();
				desno.setStyle("-fx-background-color: #094152");
				HBox c=new HBox();
			     
				c.getChildren().addAll(desno,cet);
				c.setStyle("-fx-background-color: #094152; -fx-text-box-border: transparent;");
				Scene scene2 =new Scene(c,630,630);
				stage.setScene(scene2);
				stage.show();
				
			});
			userName.setOnAction(new EventHandler<ActionEvent>(){
				
				public void handle(ActionEvent action){
					if(userName.getText().isEmpty()) {
						error.setVisible(true);
						return;
					}
					error.setText("");
					username = userName.getText();
					client.setUsername(userName.getText());
					client.sendUsername();
					
					VBox cet=makeChat();
					VBox desno=getGame();
					desno.setStyle("-fx-background-color: #094152");
					HBox c=new HBox();
				     
					c.getChildren().addAll(desno,cet);
					c.setStyle("-fx-background-color: #094152; -fx-text-box-border: transparent;");
					Scene scene2 =new Scene(c,630,630);
					stage.setScene(scene2);
					stage.show();
				}
			});
			leave.setOnAction(new EventHandler<ActionEvent>() {
				
				public void handle(ActionEvent action) {
					stage.close();
				}
			});
			
			rules.setOnAction(e->{
				VBox rule;
				try {
					rule = getRules();
					Button back= new Button("BACK");
					back.setAlignment(Pos.BOTTOM_LEFT);
					back.setTextFill(Color.BLACK);
					back.setStyle("-fx-background-color: #85b093");
					back.setFont(Font.font("Ariel",FontWeight.BOLD, 12));
					
					VBox b=new VBox();
					b.getChildren().addAll(rule,back);
					b.setStyle("-fx-background-color: #094152; -fx-text-box-border: transparent;");
					Insets insets = new Insets(10, 10, 10, 10);
					b.setPadding(insets);
					
					Scene scene3 =new Scene(b, 600,600);
					stage.setScene(scene3);
					stage.show();
					
					back.setOnAction( e1-> {
						stage.setScene(scene);
					});
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}

	public  VBox getGame() {
	    	VBox root = null;
	    	try {
	    		
	    	scores = new ArrayList<Label>();
			players = new ArrayList<Label>();
			column1 = new ArrayList<Label>();
		    column2 = new ArrayList<Label>();
		    
			ArrayList<Label> s1 = new ArrayList<Label>();
			ArrayList<Label> t1 = new ArrayList<Label>();
			
			grid = new GridPane();    
		    grid.setMinSize(400, 300); 
		    grid.setStyle("-fx-background-color: #094152"); 
		    grid.setAlignment(Pos.CENTER);
		    grid.setPadding(new Insets(10, 10, 10, 10)); 
		    grid.setVgap(5); 
		    grid.setHgap(5);
		
		    Label empty = new Label("");
		    Label ones = new Label("Ones");
		    Label twos = new Label("Twos");
		    Label threes = new Label("Threes");
		    Label fours = new Label("Fours");
		    Label fives = new Label("Fives");
		    Label sixes = new Label("Sixes");
		    Label sum = new Label("SUM");
		    Label bonus = new Label("BONUS");
		    Label threeOfAKind = new Label("Three of a kind");
		    Label fourOfAKind = new Label("Four of a kind");
		    Label fullHouse = new Label("Full house");
		    Label smallStraight = new Label("Small straight");
		    Label largeStraight = new Label("Large straight");
		    Label chance = new Label("Chance");
		    Label yahtzee = new Label("Yahtzee");
		    Label total = new Label("TOTAL");
		    
		    scores.add(empty);
		    scores.add(ones);
		    scores.add(twos);
		    scores.add(threes);
		    scores.add(fours);
		    scores.add(fives);
		    scores.add(sixes);
		    scores.add(sum);
		    scores.add(bonus);
		    scores.add(threeOfAKind);
		    scores.add(fourOfAKind);
		    scores.add(fullHouse);
		    scores.add(smallStraight);
		    scores.add(largeStraight);
		    scores.add(chance);
		    scores.add(yahtzee);
		    scores.add(total);
		    
		    for (int i = 0; i < scores.size(); i++) {
		    	scores.get(i).setPrefSize(95, 25);
		    	scores.get(i).setAlignment(Pos.CENTER);
		    	scores.get(i).setTextFill(Color.BLACK);
		    	scores.get(i).setStyle("-fx-background-color: #70a0af");
		    	scores.get(i).setFont(Font.font("Ariel", FontWeight.BOLD, 13));
		    }
		    
		    Label player1 = new Label(username);
		    Label player2 = new Label(oponentsName);
		    
		    players.add(player1);
		    players.add(player2);
		    
		    for (int i = 0; i < players.size(); i++) {
		    	players.get(i).setPrefSize(95, 25);
		    	players.get(i).setAlignment(Pos.CENTER);
		    	players.get(i).setTextFill(Color.BLACK);
		    	players.get(i).setStyle("-fx-background-color: #70a0af");
		    	players.get(i).setFont(Font.font("Ariel", FontWeight.BOLD, 13));
		    }
		    		    
	        Label ones1 = new Label("");
	        Label twos1 = new Label("");
	        Label threes1 = new Label("");
	        Label fours1 = new Label("");
	        Label fives1 = new Label("");
	        Label sixes1 = new Label("");
	        Label sum1 = new Label("");
	        Label bonus1 = new Label("");
	        Label threeOfAKind1 = new Label("");
	        Label fourOfAKind1 = new Label("");
	        Label fullHouse1 = new Label("");
	        Label smallStraight1 = new Label("");
	        Label largeStraight1 = new Label("");
	        Label chance1 = new Label("");
	        Label yahtzee1 = new Label("");
	        Label total1 = new Label("");
	        
	        column1.add(ones1);
	        column1.add(twos1);
	        column1.add(threes1);
	        column1.add(fours1);
	        column1.add(fives1);
	        column1.add(sixes1);
	        column1.add(sum1);
	        column1.add(bonus1);
	        column1.add(threeOfAKind1);
	        column1.add(fourOfAKind1);
	        column1.add(fullHouse1);
	        column1.add(smallStraight1);
	        column1.add(largeStraight1);
	        column1.add(chance1);
	        column1.add(yahtzee1);
	        column1.add(total1);
	        
	        s1.add(ones1);
	        s1.add(twos1);
	        s1.add(threes1);
	        s1.add(fours1);
	        s1.add(fives1);
	        s1.add(sixes1);
	        
	        t1.add(sum1);
	        t1.add(bonus1);
	        t1.add(threeOfAKind1);
	        t1.add(fourOfAKind1);
	        t1.add(fullHouse1);
	        t1.add(smallStraight1);
	        t1.add(largeStraight1);
	        t1.add(chance1);
	        t1.add(yahtzee1);
	        
	        for (int i = 0; i < column1.size(); i++) {
	        	column1.get(i).setPrefSize(95, 25);
	        	column1.get(i).setAlignment(Pos.CENTER);
	        	column1.get(i).setTextFill(Color.BLACK);
	        	column1.get(i).setStyle("-fx-background-color: #a0c1b9");
			    column1.get(i).setFont(Font.font("Ariel", FontWeight.BOLD, 13));
		    }
	        
	        ones1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!ones1.getText().equals("") && (ones1.getTextFill() == Color.RED || ones1.getTextFill() == Color.GRAY)) {
	        		ones1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag1 = true;
	        		for (int i = 0; i < s1.size(); i++)
	        			if (s1.get(i).getText().equals(""))
	        				flag1 = false;
	        		if (flag1 == true) {
	        			Functions.setSum(s1, sum1);
	    				Functions.setBonus(sum1, bonus1);
	        		}
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);
	        		
	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}	
	        });
	        
	        twos1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!twos1.getText().equals("") && (twos1.getTextFill() == Color.RED || twos1.getTextFill() == Color.GRAY)) {
	        		twos1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag1 = true;
	        		for (int i = 0; i < s1.size(); i++)
	        			if (s1.get(i).getText().equals(""))
	        				flag1 = false;
	        		if (flag1 == true) {
	        			Functions.setSum(s1, sum1);
	    				Functions.setBonus(sum1, bonus1);
	        		}
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	        
	        threes1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!threes1.getText().equals("") && (threes1.getTextFill() == Color.RED || threes1.getTextFill() == Color.GRAY)) {
	        		threes1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag1 = true;
	        		for (int i = 0; i < s1.size(); i++)
	        			if (s1.get(i).getText().equals(""))
	        				flag1 = false;
	        		if (flag1 == true) {
	        			Functions.setSum(s1, sum1);
	    				Functions.setBonus(sum1, bonus1);
	        		}
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	        
	        fours1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!fours1.getText().equals("") && (fours1.getTextFill() == Color.RED || fours1.getTextFill() == Color.GRAY)) {
	        		fours1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag1 = true;
	        		for (int i = 0; i < s1.size(); i++)
	        			if (s1.get(i).getText().equals(""))
	        				flag1 = false;
	        		if (flag1 == true) {
	        			Functions.setSum(s1, sum1);
	    				Functions.setBonus(sum1, bonus1);
	        		}
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	        
	        fives1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!fives1.getText().equals("") && (fives1.getTextFill() == Color.RED || fives1.getTextFill() == Color.GRAY)) {
	        		fives1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag1 = true;
	        		for (int i = 0; i < s1.size(); i++)
	        			if (s1.get(i).getText().equals(""))
	        				flag1 = false;
	        		if (flag1 == true) {
	        			Functions.setSum(s1, sum1);
	    				Functions.setBonus(sum1, bonus1);
	        		}
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	        
	        sixes1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!sixes1.getText().equals("") && (sixes1.getTextFill() == Color.RED || sixes1.getTextFill() == Color.GRAY)) {
	        		sixes1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag1 = true;
	        		for (int i = 0; i < s1.size(); i++)
	        			if (s1.get(i).getText().equals(""))
	        				flag1 = false;
	        		if (flag1 == true) {
	        			Functions.setSum(s1, sum1);
	    				Functions.setBonus(sum1, bonus1);
	        		}
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	        
	        threeOfAKind1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!threeOfAKind1.getText().equals("") && (threeOfAKind1.getTextFill() == Color.RED || threeOfAKind1.getTextFill() == Color.GRAY)) {
	        		threeOfAKind1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	        
	        fourOfAKind1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!fourOfAKind1.getText().equals("") && (fourOfAKind1.getTextFill() == Color.RED || fourOfAKind1.getTextFill() == Color.GRAY)) {
	        		fourOfAKind1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	        
	        fullHouse1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!fullHouse1.getText().equals("") && (fullHouse1.getTextFill() == Color.RED || fullHouse1.getTextFill() == Color.GRAY)) {
	        		fullHouse1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	        
	        smallStraight1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!smallStraight1.getText().equals("") && (smallStraight1.getTextFill() == Color.RED || smallStraight1.getTextFill() == Color.GRAY)) {
	        		smallStraight1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	        
	        largeStraight1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!largeStraight1.getText().equals("") && (largeStraight1.getTextFill() == Color.RED || largeStraight1.getTextFill() == Color.GRAY)) {
	        		largeStraight1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	        
	        chance1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!chance1.getText().equals("") && (chance1.getTextFill() == Color.RED || chance1.getTextFill() == Color.GRAY)) {
	        		chance1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	        
	        yahtzee1.setOnMouseClicked(e -> {
	        	if (enemyTurn)
					return;
	        	
	        	if (!yahtzee1.getText().equals("") && (yahtzee1.getTextFill() == Color.RED || yahtzee1.getTextFill() == Color.GRAY)) {
	        		yahtzee1.setTextFill(Color.BLACK);
	        		
	        		for (int i = 0; i < column1.size(); i++)
						if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
							column1.get(i).setText("");
	        		
	        		dices[0].setAside(false);
			    	dices[1].setAside(false);
			    	dices[2].setAside(false);
			    	dices[3].setAside(false);
			    	dices[4].setAside(false);
			    	
					diceImage1.setEffect(null);
					diceImage2.setEffect(null);
					diceImage3.setEffect(null);
					diceImage4.setEffect(null);
					diceImage5.setEffect(null);
					
					firstRoll = true;
	        		numberRoll = 0;
	        		rollButton.setDisable(false);
	        		
	        		boolean flag2 = true;
	        		for (int i = 0; i < t1.size(); i++)
	        			if (t1.get(i).getText().equals(""))
	        				flag2 = false;
	        		if (flag2 == true) 
	        			Functions.setTotal(t1, total1);
	        		
	        		if (!total1.getText().equals("")) 
	        			rollButton.setDisable(true);

	        		enemyTurn = true;
	        		String message = "";
	        		for (int i = 0; i < column1.size(); i++) {
	        			if (!column1.get(i).getText().equals(""))
	        				message += column1.get(i).getText();
	        			else
	        				message += "x";
	        			message += " ";
	        		}
	        		message = message.substring(0, message.length() - 1);
	        		client.sendSelect(message);
	        		
	        		if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
	    				end = true;
	    				refresh();
	    				return;
	    			}
	    			refresh();
	        	}
	        });
	          
	        Label ones2 = new Label("");
	        Label twos2 = new Label("");
	        Label threes2 = new Label("");
	        Label fours2 = new Label("");
	        Label fives2 = new Label("");
	        Label sixes2 = new Label("");
	        Label sum2 = new Label("");
	        Label bonus2 = new Label("");
	        Label threeOfAKind2 = new Label("");
	        Label fourOfAKind2 = new Label("");
	        Label fullHouse2 = new Label("");
	        Label smallStraight2 = new Label("");
	        Label largeStraight2 = new Label("");
	        Label chance2 = new Label("");
	        Label yahtzee2 = new Label("");
	        Label total2 = new Label("");
	        
	        column2.add(ones2);
	        column2.add(twos2);
	        column2.add(threes2);
	        column2.add(fours2);
	        column2.add(fives2);
	        column2.add(sixes2);
	        column2.add(sum2);
	        column2.add(bonus2);
	        column2.add(threeOfAKind2);
	        column2.add(fourOfAKind2);
	        column2.add(fullHouse2);
	        column2.add(smallStraight2);
	        column2.add(largeStraight2);
	        column2.add(chance2);
	        column2.add(yahtzee2);
	        column2.add(total2);
	        
	        for (int i = 0; i < column2.size(); i++) {
	        	column2.get(i).setPrefSize(95, 25);
	        	column2.get(i).setAlignment(Pos.CENTER);
	        	column2.get(i).setTextFill(Color.BLACK);
	        	column2.get(i).setStyle("-fx-background-color: #a0c1b9");
			    column2.get(i).setFont(Font.font("Ariel", FontWeight.BOLD, 13));
		    }
	        
		    for (int i = 0; i < scores.size(); i++)
		    	grid.add(scores.get(i), 0, i+1);
		    
		    for (int i = 0; i < players.size(); i++)
		    	grid.add(players.get(i), i+1, 1);
		    
		    for (int i = 0; i < column1.size(); i++)
		    	grid.add(column1.get(i), 1, i+2);
		    
		    for (int i = 0; i < column2.size(); i++)
		    	grid.add(column2.get(i), 2, i+2);
		    
			diceImage1 = new ImageView();
			diceImage2 = new ImageView();
			diceImage3 = new ImageView();
			diceImage4 = new ImageView();
			diceImage5 = new ImageView();
			
			File file = new File("src/addings/dice1.png");
			diceImage1.setImage(new Image(file.toURI().toString()));
			diceImage2.setImage(new Image(file.toURI().toString()));
			diceImage3.setImage(new Image(file.toURI().toString()));
			diceImage4.setImage(new Image(file.toURI().toString()));
			diceImage5.setImage(new Image(file.toURI().toString()));
	    	
	    	diceImage1.setFitHeight(50);
	        diceImage1.setFitWidth(50);
	        diceImage2.setFitHeight(50);
	        diceImage2.setFitWidth(50);
	        diceImage3.setFitHeight(50);
	        diceImage3.setFitWidth(50);
	        diceImage4.setFitHeight(50);
	        diceImage4.setFitWidth(50);
	        diceImage5.setFitHeight(50);
	        diceImage5.setFitWidth(50);
	       
	        ArrayList<ImageView> dicesImage = new ArrayList<>();
			dicesImage.add(diceImage1);
			dicesImage.add(diceImage2);
			dicesImage.add(diceImage3);
			dicesImage.add(diceImage4);
			dicesImage.add(diceImage5);
			
			dice1 = new Dice();
		    dice2 = new Dice();
			dice3 = new Dice();
			dice4 = new Dice();
			dice5 = new Dice();
	        
	        dices = new Dice[5];
			dices[0] = dice1;
			dices[1] = dice2;
			dices[2] = dice3;
			dices[3] = dice4;
			dices[4] = dice5;
			
			int[] dicesRoll = new int[6];
			dicesRoll[0] = dice1.getNumber();
			dicesRoll[1] = dice2.getNumber();
			dicesRoll[2] = dice3.getNumber();
			dicesRoll[3] = dice4.getNumber();
			dicesRoll[4] = dice5.getNumber();
			
			DropShadow borderGlow= new DropShadow();
	        borderGlow.setColor(Color.WHITE);
	        borderGlow.setOffsetY(0f);
	        borderGlow.setOffsetX(0f);
	        borderGlow.setWidth(50);
	        borderGlow.setHeight(50);
	        borderGlow.setRadius(10); 
	        borderGlow.setSpread(0.8);
	        
			diceImage1.setOnMouseClicked(e -> {
				if (enemyTurn)
					return;
				
				if (!dice1.isAside() && firstRoll == false && numberRoll < 3) {
					 dice1.setAside(true);
					 diceImage1.setEffect(borderGlow);
				}
				else {
					dice1.setAside(false);
					diceImage1.setEffect(null);
				}
			});
			
			diceImage2.setOnMouseClicked(e -> {
				if (enemyTurn)
					return;
				
				if (!dice2.isAside() && firstRoll == false && numberRoll < 3) {
					 dice2.setAside(true);
					 diceImage2.setEffect(borderGlow);
				}
				else {
					dice2.setAside(false);
					diceImage2.setEffect(null);
				}
			});
			
			diceImage3.setOnMouseClicked(e -> {
				if (enemyTurn)
					return;
				
				if (!dice3.isAside() && firstRoll == false && numberRoll < 3) {
					dice3.setAside(true);
					diceImage3.setEffect(borderGlow);
				}
				else {
					dice3.setAside(false);
					diceImage3.setEffect(null);
				}
			});
			
			diceImage4.setOnMouseClicked(e -> {
				if (enemyTurn)
					return;
				
				if (!dice4.isAside() && firstRoll == false && numberRoll < 3) {
					dice4.setAside(true);
				    diceImage4.setEffect(borderGlow);
				}
				else {
					dice4.setAside(false);
					diceImage4.setEffect(null);
				}
			});
			
			diceImage5.setOnMouseClicked(e -> {
				if (enemyTurn)
					return;
				
				if (!dice5.isAside() && firstRoll == false && numberRoll < 3) {
					dice5.setAside(true);
					diceImage5.setEffect(borderGlow);
				}
				else {
					dice5.setAside(false);
					diceImage5.setEffect(null);
				}
			});
			
			rollButton = new Button("ROLL");
			rollButton.setTextFill(Color.BLACK);
			rollButton.setStyle("-fx-background-color: #85b093");
			rollButton.setPrefHeight(50);
			rollButton.setPrefWidth(50);
			rollButton.setFont(Font.font("Ariel", FontWeight.BOLD, 13));
			
			rollButton.setOnAction (e -> {
				if (enemyTurn || !oponentConnected)
					return;
				
				if (numberRoll < 3) {
				for (int i = 0; i < column1.size(); i++)
					if (column1.get(i).getTextFill() == Color.RED || column1.get(i).getTextFill() == Color.GRAY)
						column1.get(i).setText("");
				firstRoll = false;
				numberRoll++;
		        rollButton.setDisable(true);
		        
				Thread thread = new Thread() {
					public void run() {
						try {
							for (int i = 0; i < 3; i++) {
						        for (int j = 0; j < dices.length; j++) {
						        	if (!dices[j].isAside()) {
						        		int number = random.nextInt(6) + 1;
								        File file = new File("src/addings/dice" + number + ".png");
								        dicesImage.get(j).setImage(new Image(file.toURI().toString()));
						        	}
						        }
						        Thread.sleep(5);
						    }
							
							for (int j = 0; j < dices.length; j++) {
					        	if (!dices[j].isAside()) {
					        	int number = dices[j].getNumber();
						        File file = new File("src/addings/dice" + number + ".png");
						        dicesImage.get(j).setImage(new Image(file.toURI().toString()));	
					        	}
							}
							
						    rollButton.setDisable(false);
						    if (numberRoll == 3) {
						    	dices[0].setAside(false);
						    	dices[1].setAside(false);
						    	dices[2].setAside(false);
						    	dices[3].setAside(false);
						    	dices[4].setAside(false);
								diceImage1.setEffect(null);
								diceImage2.setEffect(null);
								diceImage3.setEffect(null);
								diceImage4.setEffect(null);
								diceImage5.setEffect(null);
								rollButton.setDisable(true);
						    }
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
				};
				
				 for (int j = 0; j < dices.length; j++) 
			        	if (!dices[j].isAside()) 
			        	dices[j].setNumber();							
			    
				dicesRoll[0] = dice1.getNumber();
				dicesRoll[1] = dice2.getNumber();
				dicesRoll[2] = dice3.getNumber();
				dicesRoll[3] = dice4.getNumber();
				dicesRoll[4] = dice5.getNumber();
				
				if (ones1.getText().equals(""))
					Functions.setOnes(dicesRoll, ones1);
				
				if (twos1.getText().equals(""))
					Functions.setTwos(dicesRoll, twos1);
				
				if (threes1.getText().equals(""))
					Functions.setThrees(dicesRoll, threes1);
				
				if (fours1.getText().equals(""))
					Functions.setFours(dicesRoll, fours1);
				
				if (fives1.getText().equals(""))
					Functions.setFives(dicesRoll, fives1);
				
				if (sixes1.getText().equals(""))
					Functions.setSixes(dicesRoll, sixes1);
				
				if (threeOfAKind1.getText().equals(""))
					Functions.setThreeOfAKind(dicesRoll, threeOfAKind1);
				
				if (fourOfAKind1.getText().equals(""))
					Functions.setFourOfAKind(dicesRoll, fourOfAKind1);
				
				if (fullHouse1.getText().equals(""))
					Functions.setFullHouse(dicesRoll, fullHouse1);
				
	    		if (smallStraight1.getText().equals(""))
	    			Functions.setSmallStraight(dicesRoll, smallStraight1);
	    		
	    		if (largeStraight1.getText().equals(""))
	    			Functions.setLargeStraight(dicesRoll, largeStraight1);
	    		
	    		if (chance1.getText().equals(""))
	    			Functions.setChance(dicesRoll, chance1);
	    		
	    		if (yahtzee1.getText().equals(""))
	    			Functions.setYahtzee(dicesRoll, yahtzee1);
				
				thread.start();
				}
			});
			
			HBox t = new HBox(15);
			t.getChildren().addAll(grid);
			
			
			HBox d = new HBox(15);
			d.getChildren().addAll(rollButton, diceImage1, diceImage2, diceImage3, diceImage4, diceImage5);
			d.setStyle("-fx-background-color: #094152"); 
			Insets insets = new Insets(10,10,10,10);
			d.setPadding(insets);
			
			root = new VBox(15);
			root.getChildren().addAll(t,d);
			root.setStyle("-fx-background-color: #094152");  
			
			
		         } catch(Exception e5) {
			e5.printStackTrace();
		}
	    	return root;    	
	}
	
	private VBox getRules() throws IOException {
		Label l=new Label("\n YAHTZEE RULES \n\n");
		//l.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY )));
		l.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		l.setTextFill(Color.WHITE);
		l.setTextAlignment(TextAlignment.JUSTIFY);
		l.setAlignment(Pos.TOP_CENTER);
		//r.setTextFill(Color.RED);
	
		TextArea t=new TextArea();
		t.setEditable(false);
		t.setStyle("-fx-control-inner-background: #326d6c; -fx-text-box-border: transparent; -fx-text-fill: #ffffff; -fx-font-weight: bold");
	    t.setFont(Font.font("Ariel", FontWeight.BOLD, 12));
	    t.setMinHeight(450);
	   
		try {
			BufferedReader br=new BufferedReader(new FileReader("src/addings/rules.txt"));
			StringBuilder sb= new StringBuilder();
			String line;
			while((line=br.readLine())!=null) {
				sb.append(line);
				sb.append("\n");
			}
			
		 	t.setText(sb.toString());
			br.close();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		VBox r=new VBox();
		r.getChildren().addAll(l,t);
		r.setAlignment(Pos.CENTER);
		Insets insets = new Insets(10, 10, 10, 10);
		r.setPadding(insets);
		//r.setStyle("-fx-background-color: #4B5795; -fx-text-box-border: transparent;");
		return r;
	}
	
	 private VBox makeChat() {
	    	Label label=new Label("CHAT: ");
	    	label.setTextFill(Color.WHITE );
			label.setTextAlignment(TextAlignment.JUSTIFY);
			label.setAlignment(Pos.TOP_CENTER);
			label.setFont(Font.font("Ariel", FontWeight.BOLD, 13));
	    	
	    	chat=new TextFlow();
	    	chat.setPrefWidth(217);
	    	chat.setPrefHeight(330);
	    	chat.setStyle("-fx-background-color: #326d6c; -fx-text-box-border: transparent;  -fx-text-fill: #ffffff; -fx-font-weight: bold");
	    	chat.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,null)));
	    	chat.setPadding(new Insets(10));
	    	
	    	ScrollPane scrollPane=new ScrollPane();
	        scrollPane.setContent(chat);
	        scrollPane.setFitToWidth(true);
	        scrollPane.vvalueProperty().bind(chat.heightProperty());
	   
	 
	    	TextField input =new TextField();
			input.setStyle("-fx-background-color: #326d6c;  -fx-text-fill: #ffffff; -fx-font-weight: bold");
	    	input.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,null)));
	    	
	    	
            input.setOnAction(new EventHandler<ActionEvent> () {
	    		
	    		public void handle(ActionEvent action) {
	    			if(oponentConnected)
	    			client.sendMessage(input.getText());
	    			
	    			if(!input.getText().isEmpty()) {
	    				Text name=new Text(client.getUsername() + ": ");
	    				Paint green=Color.rgb(0,255,0);
	    				name.setFill(green);
	    				Text textMessage = new Text(input.getText() + "\n" );
	    				Paint white=Color.rgb(255,255,255);
	    				textMessage.setFill(white);
	    				ObservableList<Node> list= chat.getChildren();
	    				list.addAll(name, textMessage);
	    				input.clear();
	    			}
	                  	
	    		}
	    	});
	    	
	    	Button send= new Button("SEND");
	    	send.setTextFill(Color.BLACK);
			send.setStyle("-fx-background-color: #85b093");
	    	send.setFont(Font.font("Ariel", FontWeight.BOLD, 13));
	    	
            send.setOnAction(e ->{
	    		
	    		if(oponentConnected)
	    			client.sendMessage(input.getText());
	    			
	    			if(!input.getText().isEmpty()) {
	    				Text name=new Text(client.getUsername() + ": ");
	    				Paint green=Color.rgb(0,255,0);
	    				name.setFill(green);
	    				Text textMessage = new Text(input.getText() + "\n" );
	    				Paint white=Color.rgb(255,255,255);
	    				textMessage.setFill(white);
	    				ObservableList<Node> list= chat.getChildren();
	    				list.addAll(name, textMessage);
	    				input.clear();
	    			}
	    		
	    	}
	    	);
	    	
	    	HBox toSend=new HBox(10);
	    	toSend.setAlignment(Pos.BASELINE_RIGHT);
	    	toSend.getChildren().addAll(input,send);
	    	
	    	turn = new Label();
	    	turn.setPrefSize(155, 25);
	    	if (!enemyTurn && oponentConnected)
				 turn.setStyle("-fx-background-color: #00fa00");
			 else
				 turn.setStyle("-fx-background-color: #ff0000");
	    	
	    	Button leave= new Button("LEAVE");
	    	leave.setTextFill(Color.BLACK);
			leave.setStyle("-fx-background-color: #85b093");
	    	leave.setFont(Font.font("Ariel", FontWeight.BOLD, 13));
	    	
            leave.setOnAction(new EventHandler<ActionEvent>() {
				
				public void handle(ActionEvent action) {
					client.sendLeftGame();
					oponentConnected=false;
					end=false;
					oponentsName="";
					stage.close();
				}
			});
	    	
	    	
	    	HBox h = new HBox(10);
	    	h.getChildren().addAll(turn, leave);
	    	Insets insets = new Insets (10, 0, 0, 0);
	    	h.setPadding(insets);
	   
	    	
	    	VBox chatBox=new VBox(10);
	    	chatBox.getChildren().addAll(h, label, scrollPane, toSend);
	    	
	    	return chatBox;
	    }
	 public void addMessageToChat(String message) {            
	    	if(message.split(":").length >=2) {
	    		Text name=new Text(oponentsName+":");
	    		Paint red=Color.rgb(255,0,0);
				name.setFill(red);
	    		Text messageText=new Text(message.split(":")[1]+ "\n");
	    		Paint white=Color.rgb(255,255,255);
				messageText.setFill(white);
	    		ObservableList<Node> list = chat.getChildren();
	    	    list.addAll(name, messageText);
	    	}
	    }
	 
	 public void refresh() { 
		 Platform.runLater(new Runnable() { 
				@Override
				public void run() {
					if (oponentConnected)
						players.get(1).setText(oponentsName);
					
					if (!enemyTurn && oponentConnected && !end)
						 turn.setStyle("-fx-background-color: #00fa00");
					else
						 turn.setStyle("-fx-background-color: #ff0000");
					
					if(end) {
						VBox winner = getWinner(Integer.parseInt(column1.get(column1.size() - 1).getText()),Integer.parseInt(column2.get(column2.size() - 1).getText()));
						Scene scene4 = new Scene(winner,600,600);
						stage.setScene(scene4);
						stage.show();
					}
				}
				});
		}
	 
	 public void reset() {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					oponentConnected = false;
					enemyTurn = false;
					end = false;
                    oponentsName = "";
					client.sendUsername();
					
					VBox cet=makeChat();
					VBox desno=getGame();
					desno.setStyle("-fx-background-color: #094152");
					HBox c=new HBox();
				     
					c.getChildren().addAll(desno,cet);
					c.setStyle("-fx-background-color: #094152; -fx-text-box-border: transparent;");
					Scene scene2 =new Scene(c,630,630);
					stage.setScene(scene2);
					stage.show();
					
					refresh();
				}
			});
		}
	 
	 public void select(String select) {  
		    enemyTurn = false;
		    String[] scores = select.split(" ");
			for (int i = 0; i < column2.size(); i++)
				if (!scores[i].equals("x"))
					column2.get(i).setText(scores[i]);
				else
					column2.get(i).setText("");
			if (!column1.get(column1.size() - 1).getText().equals("") && !column2.get(column2.size() - 1).getText().equals("")) {
				end = true;
				refresh();
				return;
			}
			refresh();
		}
	 
	 public void setOponentName(String name) {
			oponentsName = name;
		}
	 
	 public void setOponentConnected() {
			oponentConnected = true;
			refresh();
		}
	 
	 public void disconnectedOponent() {
		    oponentConnected = false;
		    enemyTurn = true;
		    end = false;
		    
		    VBox oponent = getOponent();
			Scene scene5 = new Scene(oponent,600,600);
			stage.setScene(scene5);
			stage.show(); 
		}
	 
	 public void setEnemyTurn(boolean turn) {
			enemyTurn = turn;
		}
	 
	 public void stop() throws IOException {
			client.sendDisconnected();
			client.closeResourses();
		}
	 
	 public VBox getWinner(int total1, int total2) {
		 VBox winner = new VBox(10);
		 winner.setStyle("-fx-background-color: #094152");
		 
	    Label l=new Label("\n FINAL SCORE \n\n");		
		l.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		l.setTextFill(Color.WHITE);
		l.setTextAlignment(TextAlignment.JUSTIFY);
		l.setAlignment(Pos.TOP_CENTER);
			
		 Label score;
		 
		 if(total1 > total2) {
			 String info= "The WINNER is " + username+ "!" + "\n" +
		 "1. " + username + ":" + total1 + "\n" +
		 "2. " + oponentsName + ":" + total2;
			 score = new Label(info);
			 score.setMaxSize(400, 300);
			 score.setFont(Font.font("Ariel", FontWeight.BOLD, 18));
			 score.setTextFill(Color.WHITE);
			 score.setStyle("-fx-background-color: #326d6c");
		 }
		 else if( total1 < total2 ) {
			 String info= "The WINNER is " + oponentsName + "!" + "\n" +
					 "1. " + oponentsName + ":" + total2 + "\n" +
					 "2. " + username + ":" + total1;
			 score = new Label(info);
			 score.setMaxSize(400, 300);
			 score.setTextFill(Color.WHITE);
			 score.setFont(Font.font("Ariel", FontWeight.BOLD, 18));
			 score.setStyle("-fx-background-color: #326d6c");
			 
		 }
		 else {
			 String info= "There is NO winner. Do you want to play again?";
			 score = new Label(info);
			 score.setMaxSize(400, 200);
			 score.setFont(Font.font("Ariel", FontWeight.BOLD, 18));
			 score.setTextFill(Color.WHITE);
			 score.setStyle("-fx-background-color: #326d6c");
			 
		 }
		 
		 HBox buttons = new HBox(10);
		 
		 Button leaveGame = new Button("LEAVE GAME");
		 leaveGame.setMaxSize(150, 20);
		 leaveGame.setTextFill(Color.BLACK);
		 leaveGame.setStyle("-fx-background-color: #85b093");
		 leaveGame.setFont(Font.font("Ariel", FontWeight.BOLD, 13));
		 leaveGame.setOnMouseClicked(e -> {
		    client.sendLeftGame();
			oponentConnected = false;
			end = false;
			oponentsName = "";
			Platform.exit(); 
		 });
		 
		 
		 Button playAgain = new Button("PLAY AGAIN");
		 playAgain.setMaxSize(150, 20);
		 playAgain.setFont(Font.font("Ariel", FontWeight.BOLD, 13));
		 playAgain.setTextFill(Color.BLACK);
		 playAgain.setStyle("-fx-background-color: #85b093");
		 playAgain.setOnMouseClicked(e -> {
			 reset();
		 });
		
		 buttons.getChildren().addAll(leaveGame,playAgain);
		 buttons.setPadding(new Insets(10, 10, 10, 10));
		 
		 winner.getChildren().addAll(l,score, buttons);
		 winner.setPadding(new Insets(10, 10, 10, 10));
		 
		return winner;
	 }
	 
	 public VBox getOponent() {
		 VBox oponent = new VBox(10);
		 oponent.setStyle("-fx-background-color: #094152");
		 
	    Label l=new Label("\n Oponent " + oponentsName + " has left the game! \n\n");		
		l.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		l.setTextFill(Color.WHITE);
		l.setTextAlignment(TextAlignment.JUSTIFY);
		l.setAlignment(Pos.TOP_CENTER);
		l.setStyle("-fx-background-color: #326d6c");
			
		 HBox buttons = new HBox(10);
		 
		 Button leaveGame = new Button("LEAVE GAME");
		 leaveGame.setMaxSize(150, 20);
		 leaveGame.setTextFill(Color.BLACK);
		 leaveGame.setStyle("-fx-background-color: #85b093");
		 leaveGame.setFont(Font.font("Ariel", FontWeight.BOLD, 13));
		 leaveGame.setOnMouseClicked(e -> {
		    client.sendLeftGame();
			oponentConnected = false;
			end = false;
			oponentsName = "";
			Platform.exit(); 
		 });
		 

		 Button playAgain = new Button("PLAY AGAIN");
		 playAgain.setMaxSize(150, 20);
		 playAgain.setFont(Font.font("Ariel", FontWeight.BOLD, 13));
		 playAgain.setTextFill(Color.BLACK);
		 playAgain.setStyle("-fx-background-color: #85b093");
		 playAgain.setOnMouseClicked(e -> {
			 reset();
		 });
		
		 buttons.getChildren().addAll(leaveGame,playAgain);
		 buttons.setPadding(new Insets(10, 10, 10, 10));
		 
		 oponent.getChildren().addAll(l, buttons);
		 oponent.setPadding(new Insets(10, 10, 10, 10));
		 
		return oponent;
	 }
	
	 public static void main(String[] args) {
		    launch(args);
	    }
}
