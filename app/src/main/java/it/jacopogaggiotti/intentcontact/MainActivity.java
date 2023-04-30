package it.jacopogaggiotti.intentcontact;

import static android.Manifest.permission.READ_CONTACTS;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    String[] permissions = {READ_CONTACTS};
    private static final int PICK_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this,READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(permissions,1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        View btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

                if(intent.resolveActivity(getPackageManager()) != null) { // da capire pk su alcune versioni di android funziona e altre no
                    if(ContextCompat.checkSelfPermission(getApplicationContext(),READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                        startActivityForResult(intent,PICK_CONTACT);
                    } else {
                        requestPermissions(permissions,2);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(
                    contactUri,
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) { //forse si può usare solo cursor.moveToFirst();
                int colonnaNumero = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String numero = cursor.getString(colonnaNumero);

                int colonnaNome = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String nome = cursor.getString(colonnaNome);

                String dati = numero+"\n"+nome;

                TextView txt1 = findViewById(R.id.textView1);
                txt1.setText(dati);

                cursor.close();
            }
        }
    }
}