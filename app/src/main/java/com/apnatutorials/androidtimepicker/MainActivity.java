package com.apnatutorials.androidtimepicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvFromTime, tvToDate;
    DateFormat dateFormatter = new SimpleDateFormat(TIME_FORMAT);
    int callerId = -1;
    //  public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final String TIME_FORMAT = "hh:mm a";

    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvToDate = (TextView) findViewById(R.id.tvToTime);
        tvToDate.setOnClickListener(this);
        tvFromTime = (TextView) findViewById(R.id.tvFromTime);
        tvFromTime.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        TextView t = (TextView) view;
        switch (view.getId()) {
            case R.id.tvFromTime:
                showTimePickerDialog(t.getId(), t.getText().toString());
                break;
            case R.id.tvToTime:
                showTimePickerDialog(t.getId(), t.getText().toString());
                break;
        }
    }

    /**
     * @param callerId
     * @param timeText
     */
    public void showTimePickerDialog(int callerId, String timeText) {
        this.callerId = callerId;
        Date date;
        try {
            if (timeText.equals(""))
                date = new Date();
            else
                date = dateFormatter.parse(timeText);
        } catch (Exception exp) {
            // In case of expense initializa date with new Date
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        boolean is24HourView = false;
        // Toast.makeText(MainActivity.this, "Hour of the day : "+ hour + " hour: " + calendar.get(Calendar.HOUR_OF_DAY) , Toast.LENGTH_SHORT).show();
        TimePickerDialog timePicker = new TimePickerDialog(ctx, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                handleOnTimeSet(hour, minute);
            }
        }, hour, minute, is24HourView);

        timePicker.setTitle("My time picker");
        timePicker.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", timePicker);
        timePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Cancel button clicked
            }
        });
        timePicker.show();
    }

    public void handleOnTimeSet(int hours, int minutes) {
        Toast.makeText(MainActivity.this, "Hours: " + hours + " Minutes: " + minutes, Toast.LENGTH_SHORT).show();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        String formatedTime = dateFormatter.format(calendar.getTime());
        Toast.makeText(MainActivity.this, "Formated time: " + formatedTime, Toast.LENGTH_SHORT).show();

        switch (callerId) {
            case R.id.tvToTime:
                tvToDate.setText(formatedTime);
                break;
            case R.id.tvFromTime:
                tvFromTime.setText(formatedTime);
                break;
        }
    }
}
