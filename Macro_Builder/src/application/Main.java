package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;


@SuppressWarnings("unused")
public class Main extends Application {
	TextField txfSpellName = new TextField("Spell");
	TextField txfCastStat = new TextField("4");
	TextField txfDamage = new TextField("Fire");
	TextField txfCritSuc = new TextField();
	TextField txfSuc = new TextField();
	TextField txfFail = new TextField();
	TextField txfCritFail = new TextField();
	
	RadioButton rbAttack = new RadioButton("Attack");
	RadioButton rbSave = new RadioButton("Save");
	RadioButton rbBoth = new RadioButton("Both");
	ToggleGroup tgpAtkSv = new ToggleGroup();
	
	RadioButton rbFort = new RadioButton("Fortitude");
	RadioButton rbRef = new RadioButton("Reflex");
	RadioButton rbWill = new RadioButton("Will");
	ToggleGroup tgpSave = new ToggleGroup();
	
	ComboBox<String> cbProf = new ComboBox<>();
	Spinner<Integer> spnLevel = new Spinner<>(1, 20, 1);
	Spinner<Integer> spnDice = new Spinner<>(0, 20, 1);
	ComboBox<String> cbDice = new ComboBox<>();
	
	TextArea txaDisplay = new TextArea();
	Button btnGenerate = new Button("Generate");
	Button btnClear = new Button("Clear");
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = buildGui();
			Scene scene = new Scene(root,500,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Pane buildGui() {
		GridPane gp = new GridPane();
		gp.add(buildNameBox(), 0, 0);
		gp.add(buildNumbersBox(), 0, 1);
		gp.add(buildDamageBox(), 0, 2);
		gp.add(buildRbBox(), 1, 0);
		gp.add(buildButtonBox(), 1, 3);
		txaDisplay.setWrapText(true);
		txaDisplay.setEditable(false);
		gp.add(txaDisplay, 0, 4);
		GridPane.setColumnSpan(txaDisplay, 2);
		Pane succBox = buildSuccBox();
		gp.add(succBox, 1, 1);
		GridPane.setRowSpan(succBox, 2);
		return gp;
	}
	
	private Pane buildNameBox() {
		VBox col = new VBox();
		col.getChildren().addAll(buildNameRow(), buildModRow());
		return col;
	}
	
	private Pane buildNameRow() {
		HBox row = new HBox();
		row.getChildren().addAll(new Label("Spell Name:"), txfSpellName);
		return row;
	}
	private Pane buildNumbersBox() {
		VBox col = new VBox();
		col.getChildren().addAll(buildProficiencyRow(), buildLevelRow());
		return col;
	}
	
	private Pane buildModRow() {
		HBox row = new HBox();
		row.getChildren().addAll(new Label("Stat Mod:"), txfCastStat);
		return row;
	}
	private Pane buildProficiencyRow() {
		HBox row = new HBox();
		cbProf.getItems().addAll("2[Trained]", "4[Expert]", "6[Master]", "8[Legendary]");
		cbProf.setValue("2[Trained]");
		row.getChildren().addAll(new Label("Proficiency:"), cbProf);
		return row;
	}
	private Pane buildLevelRow() {
		HBox row = new HBox();
		row.getChildren().addAll(new Label("Level:"), spnLevel);
		return row;
	}
	
	private Pane buildButtonBox() {
		HBox row = new HBox();
		btnGenerate.setOnAction(new GenerateButtonEventHandler() );
		btnClear.setOnAction(new ClearButtonEventHandler() );
		row.getChildren().addAll(btnGenerate, btnClear);
		return row;
	}
	
	private Pane buildRbBox() {
		HBox row = new HBox();
		row.getChildren().addAll(buildTypeBox(), buildSaveBox());
		row.setAlignment(Pos.BASELINE_LEFT);
		return row;
	}
	
	private Pane buildTypeBox() {
		VBox col = new VBox();
		rbAttack.setToggleGroup(tgpAtkSv);
		rbAttack.setSelected(true);
		rbSave.setToggleGroup(tgpAtkSv);
		rbBoth.setToggleGroup(tgpAtkSv);
		col.getChildren().addAll(rbAttack, rbSave, rbBoth);
		return col;
	}
	
	private Pane buildSaveBox() {
		VBox col = new VBox();
		rbFort.setToggleGroup(tgpSave);
		rbFort.setSelected(true);
		rbRef.setToggleGroup(tgpSave);
		rbWill.setToggleGroup(tgpSave);
		col.getChildren().addAll(rbFort, rbRef, rbWill);
		return col;
	}
	
	private Pane buildDamageBox() {
		VBox col = new VBox();
		HBox row = new HBox();
		cbDice.getItems().addAll("d4", "d6", "d8", "d10", "d12", "d20");
		cbDice.setValue("d4");
		row.getChildren().addAll(new Label("Damage:"), spnDice, cbDice);
		col.getChildren().addAll(row, buildDTypeBox());
		return col;
	}
	
	private Pane buildDTypeBox() {
		HBox row = new HBox();
		row.getChildren().addAll(new Label("Type:"), txfDamage);
		return row;
	}
	
	private Pane buildSuccBox() {
		GridPane gp = new GridPane();
		gp.add(new Label("Crit"), 0, 0);
		gp.add(txfCritSuc, 1, 0);
		gp.add(new Label("Success"), 0, 1);
		gp.add(txfSuc, 1, 1);
		gp.add(new Label("Fail"), 0, 2);
		gp.add(txfFail, 1, 2);
		gp.add(new Label("Crit Fail"), 0, 3);
		gp.add(txfCritFail, 1, 3);
		return gp;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private class GenerateButtonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			String name = txfSpellName.getText();
			String type = ((RadioButton)tgpAtkSv.getSelectedToggle()).getText();
			String critSucc = txfCritSuc.getText();
			String succ = txfSuc.getText();
			String fail = txfFail.getText();
			String critFail = txfFail.getText();
			String msg = "/me casts " + name + "\n" + 
					"&{template:default} {{name=" + name + "}} ";
			switch (type) {
			case "Attack"	: msg += attackStr(); break;
			case "Save"		: msg += saveStr(); break;
			case "Both"		: msg += attackStr() + saveStr(); break;
			}
			msg += damageStr();
			if (!critSucc.isEmpty()) {
				msg += "{{Crit = " + critSucc + " }} ";
			}
			if (!succ.isEmpty()) {
				msg += "{{Success = " + succ + " }} ";
			}
			if (!fail.isEmpty()) {
				msg += "{{Fail = " + fail + " }} ";
			}
			if (!critFail.isEmpty()) {
				msg += "{{Crit Fail = " + critFail + " }} ";
			}

//			"{{ Damage = [[1d6]] Fire }}  
//			{{ Crit = Double Damage, Dazzled for [[1]] Minute }} 
//			{{Hit = Dazzled for [[1]] Round}}");
			txaDisplay.setText(msg);
		}
		
		private String attackStr() {
			String msg = "{{Attack = [[1d20 + ";
			msg += txfCastStat.getText() + "[Stat] + ";
			msg += spnLevel.getValue().toString() + "[Level] +";
			msg += cbProf.getValue();
			return msg + " ]] }} ";
		}
		
		private String saveStr() {
			String msg = "{{Save = [[10 + ";
			msg += txfCastStat.getText() + "[Stat] + ";
			msg += spnLevel.getValue().toString() + "[Level] +";
			msg += cbProf.getValue() + " ]] ";
			msg += ((RadioButton)tgpSave.getSelectedToggle()).getText() + " }} ";
			return msg;
		}
		
		private String damageStr() {
			String msg = "{{Damage = [[ ";
			msg += spnDice.getValue() + cbDice.getValue() + " ]] ";
			msg += txfDamage.getText() + " }} ";
			return msg;
		}
			
	}
	
	private class ClearButtonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			txfSpellName.setText("Spell");
			txaDisplay.clear();
		}
			
	}
}
