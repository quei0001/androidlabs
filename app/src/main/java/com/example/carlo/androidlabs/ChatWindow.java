package com.example.carlo.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ChatWindow extends AppCompatActivity {

    ListView list;
    EditText edit;
    Button sendButton;
    ArrayList<String> listArray = new ArrayList<>();
    protected static final String ACTIVITY_NAME = "ChatWindow";
    ChatAdapter messageAdapter;

    protected ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();


        //in this case, “this” is the ChatWindow, which is-A Context object

        list = findViewById(R.id.listView1);
        edit = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);

        messageAdapter = new ChatAdapter(this);
        list.setAdapter(messageAdapter);

        Cursor cursor = db.query(false, ChatDatabaseHelper.DATABASE_NAME,
                new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                null, null, null, null, null, null);
        int rows = cursor.getCount(); //number of rows returned

        Log.i(ACTIVITY_NAME, "Cursor column count =" + cursor.getColumnCount());

        cursor.moveToFirst(); //move to first result

        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            listArray.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();

        }

        for(int i = 0; i < cursor.getColumnCount(); i++){
            cursor.getColumnCount();
            Log.i(ACTIVITY_NAME, "column count" + cursor.getColumnName(i));
        }


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edit.getText().toString();

                ContentValues newValues = new ContentValues();

                newValues.put(ChatDatabaseHelper.KEY_MESSAGE, edit.getText().toString());
                db.insert(ChatDatabaseHelper.DATABASE_NAME, message, newValues);


                listArray.add(edit.getText().toString());

                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/ getView()
                edit.setText("");
            }
        });




    }//on create

    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        db.close();
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context context) {

            super(context, 0);
        }

        public int getCount() {

            return listArray.size();
        }

        public String getItem(int position) {

            return listArray.get(position);
        }

        //primeiro parametro é a posição na view, segundo outro View reclicavel e terceiro será o viewgroup
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;

        }

    }//ChatAdapter

}
