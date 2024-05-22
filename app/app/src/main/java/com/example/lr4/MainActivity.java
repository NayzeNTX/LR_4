package com.example.lr4;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    SQLHelper database;
    LinearLayout listLinearLayout;
    LinearLayout row;

    Dialog addNewRouteDialog;
    Dialog deleteRouteDialog;
    Dialog updateRouteDialog;
    Dialog showRouteByIDDialog;

    ArrayList<TrainRoute> trainRoutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new SQLHelper(this);

        listLinearLayout = findViewById(R.id.list);

        addNewRouteDialog = new Dialog(this);
        addNewRouteDialog.setContentView(R.layout.add_new_route_dialog);

        showRouteByIDDialog = new Dialog(this);
        showRouteByIDDialog.setContentView(R.layout.show_by_id_dialog);

        updateRouteDialog = new Dialog(this);
        updateRouteDialog.setContentView(R.layout.update_route_dialog);

        deleteRouteDialog = new Dialog(this);
        deleteRouteDialog.setContentView(R.layout.delete_route_dialog);
    }

    //метод для вывода записи по id
    public void showTrainRoute(int id){

        TrainRoute t = trainRoutes.get(id-1);

        row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);

        TextView Id = new TextView(this);
        Id.setText( Integer.toString(t._id));
        Id.setWidth(120);

        TextView route = new TextView(this);
        route.setText(t.route);
        route.setWidth(500);

        TextView departureDate = new TextView(this);
        departureDate.setText(t.departureDate);
        departureDate.setWidth(120);

        TextView arrivalDate = new TextView(this);
        arrivalDate.setText(t.arrivalDate);
        arrivalDate.setWidth(120);

        row.addView(Id);
        row.addView(route);
        row.addView(departureDate);
        row.addView(arrivalDate);

        listLinearLayout.addView(row);
    }
    //обработчик для вывода всех записей на экран
    public void showAllRoutes(View view){
        trainRoutes = getList();
        listLinearLayout.removeAllViews();
        for(int i = 1; i < trainRoutes.size()+1; i++) {
            showTrainRoute(i);
        }
    }
    //обработчик для открытия диалогового окна добавления записи
    public void onClickAddButton(View v){
        addNewRouteDialog.show();
    }

    public void onClickAddConfirmationButton(View v){
        EditText routeNameEditText = addNewRouteDialog.findViewById(R.id.route_name_edittext);
        EditText departureDateEditText = addNewRouteDialog.findViewById(R.id.departure_date_edittext);
        EditText arrivalDateEditText = addNewRouteDialog.findViewById(R.id.arrival_date_edittext);
        String routeName = routeNameEditText.getText().toString();

        String departureDate = departureDateEditText.getText().toString();
        String arrivalDate = arrivalDateEditText.getText().toString();
        database.addTrainRoute(routeName, departureDate, arrivalDate);

        routeNameEditText.setText("");
        departureDateEditText.setText("");
        arrivalDateEditText.setText("");

        showAllRoutes(v);
    }
    //обработчик для открытия диалогового окна изменения записи
    public void onClickEditButton(View v){
        updateRouteDialog.show();
    }

    public void onClickEditConfirmationButton(View v){
        EditText idEditText = updateRouteDialog.findViewById(R.id.route_id_edittext);
        EditText routeNameEditText = updateRouteDialog.findViewById(R.id.route_name_edittext);
        EditText departureDateEditText = updateRouteDialog.findViewById(R.id.departure_date_edittext);
        EditText arrivalDateEditText = updateRouteDialog.findViewById(R.id.arrival_date_edittext);

        int id = Integer.parseInt(idEditText.getText().toString());
        String routeName = routeNameEditText.getText().toString();
        String departureDate = departureDateEditText.getText().toString();
        String arrivalDate = arrivalDateEditText.getText().toString();

        database.updateRoute(id ,routeName, departureDate, arrivalDate);

        idEditText.setText("");
        routeNameEditText.setText("");
        departureDateEditText.setText("");
        arrivalDateEditText.setText("");

        showAllRoutes(v);
    }
    //обработчик для открытия диалогового окна вывода записи на экран по указанному ID
    public void onClickShowByIDButton(View v){
        showRouteByIDDialog.show();
    }

    public void onClickShowByIDApplyButton(View v){
        EditText editText = showRouteByIDDialog.findViewById(R.id.help);
        int id = Integer.parseInt(editText.getText().toString());
        listLinearLayout.removeAllViews();
        showTrainRoute(id);
        editText.setText("");
    }

    //обработчик для открытия диалогового окна удаления записи по указанному ID
    public void onClickDeleteRouteButton(View v){
        deleteRouteDialog.show();
    }

    public void onClickDeleteRouteApplyButton(View v){
        EditText editText = deleteRouteDialog.findViewById(R.id.route_id_edittext);
        int id = Integer.parseInt(editText.getText().toString());
        database.deleteRoute(id);
        editText.setText("");
        showAllRoutes(v);
    }

    public ArrayList<TrainRoute> getList(){
        ArrayList<TrainRoute> trainRoutes = new ArrayList<>();
        Cursor cursor = database.getFullTable();
        if (cursor != null){
            while (cursor.moveToNext()){
                trainRoutes.add(
                        new TrainRoute(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3)));
            }
        }
        return trainRoutes;
    }
}