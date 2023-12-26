package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class Main extends Application {
	/* TODO 
	 * 	add save Proficiency selection
	 * 	finish generate Defense action event
	 * 	add weapon attack tab
	 */
	
	static String[] profList = {"0[Untrained]", "2[Trained]", "4[Expert]", "6[Master]", "8[Legendary]"};
	static String[] statList = {"STR", "DEX", "CON", "INT", "WIS", "CHA"};
	static String[] diceList = {"d4", "d6", "d8", "d10", "d12", "d20"};
	TabPane tabPane = new TabPane();
	
	TextField txfName = new TextField("name");
	Spinner<Integer> spnStr = new Spinner<>(-5, 7, 0);
	Spinner<Integer> spnDex = new Spinner<>(-5, 7, 0);
	Spinner<Integer> spnCon = new Spinner<>(-5, 7, 0);
	Spinner<Integer> spnInt = new Spinner<>(-5, 7, 0);
	Spinner<Integer> spnWis = new Spinner<>(-5, 7, 0);
	Spinner<Integer> spnCha = new Spinner<>(-5, 7, 0);
	
	ComboBox<String> cbArmorProf = new ComboBox<>();
	ComboBox<String> cbFortProf = new ComboBox<>();
	ComboBox<String> cbRefProf = new ComboBox<>();
	ComboBox<String> cbWillProf = new ComboBox<>();
	
	Spinner<Integer> spnACBon = new Spinner<>(0, 10, 0);
	Spinner<Integer> spnMaxDex = new Spinner<>(0, 7, 0);
	Spinner<Integer> spnSaveBon = new Spinner<>(0, 10, 0);
	
	
	TextField txfSpellName = new TextField("Spell");
	ComboBox<String> cbCastStat = new ComboBox<>();
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
	
	ComboBox<String> cbSpellProf = new ComboBox<>();
	
	Spinner<Integer> spnLevel = new Spinner<>(1, 20, 1);
	Spinner<Integer> spnDice = new Spinner<>(0, 20, 0);
	ComboBox<String> cbDice = new ComboBox<>();
	
	TextArea txaDefDisplay = new TextArea();
	TextArea txaSpellDisplay = new TextArea();
	Button btnDefGenerate = new Button("Generate");
	Button btnSpellGenerate = new Button("Generate");
	Button btnSpellClear = new Button("Clear");
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = buildGui();
			Scene scene = new Scene(root,500,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Pane buildGui() {
		Pane pan = buildTabPane();
		return pan;
	}
	
	private Pane buildTabPane() {
		BorderPane brdPane = new BorderPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		// Build Tab 1
		Tab tab1 = new Tab();
		tab1.setText("Character");
		tab1.setContent(buildCharacterPane());
		// Build Tab 2
		Tab tab2 = new Tab();
		tab2.setText("Spells");
		tab2.setContent(buildSpellPane());
		// Add tabs to TabPane
		tabPane.getTabs().addAll(tab1, tab2);
		// Add TabPane to BorderPane
		brdPane.setCenter(tabPane);
		return brdPane;
	}
	
	private Pane buildCharacterPane() {
		GridPane gp = new GridPane();
		gp.add(buildNameBox(), 0, 0);
		gp.add(buildStatBox(), 1, 0, 1, 2);
		gp.add(buildArmorBox(), 0, 1);
		gp.add(txaDefDisplay, 0, 3, 2, 1);
		gp.add(buildCharButtonBox(), 1, 2);
		return gp;
	}
	
	private Pane buildNameBox() {
		VBox col = new VBox();
		col.getChildren().addAll(buildNameRow(), buildLevelRow());
		return col;
	}
	
	private Pane buildNameRow() {
		HBox row = new HBox();
		row.getChildren().addAll(new Label("Name:"), txfName);
		return row;
	}
	
	private Pane buildStatBox() {
		GridPane gp = new GridPane();
		gp.add(new Label("STR"), 0, 0);
		gp.add(new Label("DEX"), 0, 1);
		gp.add(new Label("CON"), 0, 2);
		gp.add(new Label("INT"), 0, 3);
		gp.add(new Label("WIS"), 0, 4);
		gp.add(new Label("CHA"), 0, 5);
		gp.add(spnStr, 1, 0);
		gp.add(spnDex, 1, 1);
		gp.add(spnCon, 1, 2);
		gp.add(spnInt, 1, 3);
		gp.add(spnWis, 1, 4);
		gp.add(spnCha, 1, 5);
		return gp;
	}
	
	private Pane buildArmorBox() {
		GridPane gp = new GridPane();
		cbArmorProf.getItems().addAll(profList);
		cbArmorProf.setValue(profList[1]);
		gp.add(new Label("Armor Prof"), 0, 0);
		gp.add(cbArmorProf, 1, 0);
		gp.add(new Label("AC Bonus"), 0, 1);
		gp.add(spnACBon, 1, 1);
		gp.add(new Label("Max Dex"), 0, 2);
		gp.add(spnMaxDex, 1, 2);
		gp.add(new Label("Save Bonus"), 0, 3);
		gp.add(spnSaveBon, 1, 3);
		return gp;
	}
	
	private Pane buildCharButtonBox() {
		HBox row = new HBox();
		btnDefGenerate.setOnAction(new GenerateDefButtonEventHandler() );
		row.getChildren().addAll(btnDefGenerate);
		return row;
	}
	
	private Pane buildSpellPane() {
		GridPane gp = new GridPane();
		gp.add(buildSpellNameBox(), 0, 0);
		gp.add(buildNumbersBox(), 0, 1);
		gp.add(buildDamageBox(), 0, 2);
		gp.add(buildRadioBox(), 1, 0);
		gp.add(buildButtonBox(), 1, 3);
		txaSpellDisplay.setWrapText(true);
		txaSpellDisplay.setEditable(false);
		gp.add(txaSpellDisplay, 0, 4);
		GridPane.setColumnSpan(txaSpellDisplay, 2);
		Pane succBox = buildSuccBox();
		gp.add(succBox, 1, 1);
		GridPane.setRowSpan(succBox, 2);
		return gp;
	}
	
	private Pane buildSpellNameBox() {
		VBox col = new VBox();
		col.getChildren().addAll(buildSpellNameRow(), buildModRow());
		return col;
	}
	
	private Pane buildSpellNameRow() {
		HBox row = new HBox();
		row.getChildren().addAll(new Label("Spell Name:"), txfSpellName);
		return row;
	}
	
	private Pane buildNumbersBox() {
		VBox col = new VBox();
		col.getChildren().addAll(buildProficiencyRow());
		return col;
	}
	
	private Pane buildModRow() {
		HBox row = new HBox();
		cbCastStat.getItems().addAll(statList);
		cbCastStat.setValue(statList[3]);
		row.getChildren().addAll(new Label("Stat Mod:"), cbCastStat);
		return row;
	}
	private Pane buildProficiencyRow() {
		HBox row = new HBox();
		cbSpellProf.getItems().addAll(profList);
		cbSpellProf.setValue(profList[1]);
		row.getChildren().addAll(new Label("Proficiency:"), cbSpellProf);
		return row;
	}
	private Pane buildLevelRow() {
		HBox row = new HBox();
		row.getChildren().addAll(new Label("Level:"), spnLevel);
		return row;
	}
	
	private Pane buildButtonBox() {
		HBox row = new HBox();
		btnSpellGenerate.setOnAction(new GenerateSpellButtonEventHandler() );
		btnSpellClear.setOnAction(new ClearButtonEventHandler() );
		row.getChildren().addAll(btnSpellGenerate, btnSpellClear);
		return row;
	}
	
	private Pane buildRadioBox() {
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
		cbDice.getItems().addAll(diceList);
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
	
	private class GenerateDefButtonEventHandler implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent event) {
			
			String msg = "/me braces themself\n" + 
					"&{template:default} {{name= defences}} ";
			
			txaDefDisplay.setText(msg);
		}
	}
	
	private class GenerateSpellButtonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			String name = txfSpellName.getText();
			String stat = cbCastStat.getValue();
			String type = ((RadioButton)tgpAtkSv.getSelectedToggle()).getText();
			String critSucc = txfCritSuc.getText();
			String succ = txfSuc.getText();
			String fail = txfFail.getText();
			String critFail = txfFail.getText();
			String msg = "/me casts " + name + "\n" + 
					"&{template:default} {{name=" + name + "}} ";
			switch (type) {
			case "Attack"	: msg += attackStr(stat); break;
			case "Save"		: msg += saveStr(stat); break;
			case "Both"		: msg += attackStr(stat) + saveStr(stat); break;
			}
			if (spnDice.getValue()>0) {
				msg += damageStr();
			}
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

			txaSpellDisplay.setText(msg);
		}
		
		private String attackStr(String stat) {
			String msg = "{{Attack = [[1d20 + ";
			msg += statMod(stat) + "[" + stat + "] + ";
			msg += spnLevel.getValue().toString() + "[Level] +";
			msg += cbSpellProf.getValue();
			return msg + " ]] }} ";
		}
		
		private String saveStr(String stat) {
			String msg = "{{Save = [[10 + ";
			msg += statMod(stat) + "[" + stat + "] + ";
			msg += spnLevel.getValue().toString() + "[Level] +";
			msg += cbSpellProf.getValue() + " ]] ";
			msg += ((RadioButton)tgpSave.getSelectedToggle()).getText() + " }} ";
			return msg;
		}
		
		private String damageStr() {
			String msg = "{{Damage = [[ ";
			msg += spnDice.getValue() + cbDice.getValue() + " ]] ";
			msg += txfDamage.getText() + " }} ";
			return msg;
		}
		
		private String statMod(String stat) {
			switch (stat) {
			case "STR": return spnStr.getValue().toString();
			case "DEX": return spnDex.getValue().toString();
			case "CON": return spnCon.getValue().toString();
			case "INT": return spnInt.getValue().toString();
			case "WIS": return spnWis.getValue().toString();
			case "CHA": return spnCha.getValue().toString();
			}
			return null;
		}
			
	}
	
	private class ClearButtonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			txfSpellName.setText("Spell");
			txaSpellDisplay.clear();
			txfCritSuc.clear();
			txfSuc.clear();
			txfFail.clear();
			txfCritFail.clear();
		}
			
	}
}
