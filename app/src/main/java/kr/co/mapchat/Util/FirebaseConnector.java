package kr.co.mapchat.Util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseConnector {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private String table;

    public FirebaseConnector(String table){
        this.table = table;
    }

    public void insertData(Object obj){
        databaseReference.child(table).push().setValue(obj);
    }
}
