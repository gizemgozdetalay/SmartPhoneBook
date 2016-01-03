package com.example.gizem.smartphonebook;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.support.v7.appcompat.*;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView list;
    ContactDB db;
    // String number;
   // List<Contact> contactArrayList;
   //  private ContactAdapter adapter = new ContactAdapter(this,contactArrayList);
    static final int PICK_CONTACT_REQUEST=1;
   // ArrayList<String> kisiler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton add, refresh;
        ContactAdapter adapter;
        final List<Contact> contactArrayList;
        list = (ListView)findViewById(R.id.listView);
        db = new ContactDB(getApplicationContext());


        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (phones.moveToNext())
        {
            //
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number =phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ;
            //System.out.println(number);
            // int i = Integer.parseInt(number);
            //  System.out.println(i);
            Contact c = new Contact(name,number);
            db.addContact(c);
        }
        phones.close();
        contactArrayList=db.getAllStudentsList();
        adapter=new ContactAdapter(this,contactArrayList);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

     //  refreshContacts(list,adapter);
        //  final ContactAdapter adapter;
        //   final ListView list;
        //  db = new ContactDB(getApplicationContext());
    //  adapter=new ContactAdapter(this,contactArrayList);
    //    list.setAdapter(adapter);
        //contactArrayList=db.getAllStudentsList();
        // refreshContacts(list,contactArrayList);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                araDialog dialog = new araDialog(MainActivity.this, contactArrayList.get(position).getNumber());
                //  Log.e("No :",contactArrayList.get(position).getNumber());
                dialog.show();
               /* AlertDialog.Builder  dialogBuilder =
                        new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(contactArrayList.));*/

            }

            ;
        });
        add=(ImageButton)findViewById(R.id.imageButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                startActivityForResult(intent, PICK_CONTACT_REQUEST);


            }
        });
        refresh=(ImageButton)findViewById(R.id.refresh);
                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshContacts(list,contactArrayList);


                    }
                });
        }
    protected void refreshContacts(ListView list, List<Contact> contactArrayList)
    {
        ContactAdapter adapter;

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (phones.moveToNext())
        {
           //
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number =phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ;
            //System.out.println(number);
            // int i = Integer.parseInt(number);
            //  System.out.println(i);
            Contact c = new Contact(name,number);
            db.addContact(c);
        }
        phones.close();
        contactArrayList=db.getAllStudentsList();
         adapter=new ContactAdapter(this,contactArrayList);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

    }


    public class araDialog extends Dialog {
          String number;
        Button call,message,statistics;

        public araDialog(Context context, final String number) {
            super(context);
            this.number=number;
            setContentView(R.layout.dialog_layout);

            final LinearLayout sendLayout = (LinearLayout)findViewById(R.id.sendlayout);
            final LinearLayout choiceLayout = (LinearLayout)findViewById(R.id.choiceLayout);

            call= (Button)findViewById(R.id.button);
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("number", number);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+number));
                    Uri no = intent.getData();
                    System.out.println(no);
                    startActivity(intent);
                }
            });
            message = (Button)findViewById(R.id.button2);
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (sendLayout.getVisibility()==View.INVISIBLE)
                        sendLayout.setVisibility(View.VISIBLE);
                    choiceLayout.setVisibility(View.INVISIBLE);
                    Button send = (Button)findViewById(R.id.sentButton);
                    final EditText editText = (EditText)findViewById(R.id.editText);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(number, null, editText.getText().toString(), null, null);
                            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_SHORT).show();
                            sendLayout.setVisibility(View.INVISIBLE);
                            choiceLayout.setVisibility(View.VISIBLE);
                          /*  Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("smsto:"+number));
                            intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
                            startActivity(intent);*/

                        }
                    });

                }
            });

        }


        }
    }







