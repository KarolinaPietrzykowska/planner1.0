package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class Controller
{
	private Model model = new Model();
	
	public Model getModel()
	{
		return model;
	}

	// Pierwszy tydzień miesiąca
	@FXML
	private TextArea A0;

	@FXML
	private TextArea A1;

	@FXML
	private TextArea A2;

	@FXML
	private TextArea A3;

	@FXML
	private TextArea A4;

	@FXML
	private TextArea A5;

	@FXML
	private TextArea A6;

	// Drugi tydzień miesiąca
	@FXML
	private TextArea B0;

	@FXML
	private TextArea B1;

	@FXML
	private TextArea B2;

	@FXML
	private TextArea B3;

	@FXML
	private TextArea B4;

	@FXML
	private TextArea B5;

	@FXML
	private TextArea B6;

	// Trzeci tydzień miesiąca
	@FXML
	private TextArea C0;

	@FXML
	private TextArea C1;

	@FXML
	private TextArea C2;

	@FXML
	private TextArea C3;

	@FXML
	private TextArea C4;

	@FXML
	private TextArea C5;

	@FXML
	private TextArea C6;

	// Czwarty tydzień miesiąca
	@FXML
	private TextArea D0;

	@FXML
	private TextArea D1;

	@FXML
	private TextArea D2;

	@FXML
	private TextArea D3;

	@FXML
	private TextArea D4;

	@FXML
	private TextArea D5;

	@FXML
	private TextArea D6;

	// Piąty tydzień miesiąca
	@FXML
	private TextArea E0;

	@FXML
	private TextArea E1;

	@FXML
	private TextArea E2;

	@FXML
	private TextArea E3;

	@FXML
	private TextArea E4;

	@FXML
	private TextArea E5;

	@FXML
	private TextArea E6;

	// Szósty tydzień miesiąca
	@FXML
	private TextArea F0;

	@FXML
	private TextArea F1;

	@FXML
	private TextArea F2;

	@FXML
	private TextArea F3;

	@FXML
	private TextArea F4;

	@FXML
	private TextArea F5;

	@FXML
	private TextArea F6;

	// Akcje przycisków

	// Przycisk do odświeżania
	@FXML
	private void refreshButtonOnAction(ActionEvent event)
	{
		model.getDataFromDatabaseToCalendar();
	}

	// Przycisk do zmiany obowiązków
	@FXML
	private void changeResponsibilityButtonOnAction(ActionEvent event)
	{
	}

	// Przycisk do anulowania obowiązków
	@FXML
	private void cancelResponsibilityButtonOnAction(ActionEvent event)
	{

	}

	// Przycisk do dodawania adnotacji
	@FXML
	private void addAdnotationButtonOnAction(ActionEvent event)
	{

	}
}
