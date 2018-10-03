package afpa.cdi.db.demo_premiere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void essai(View btn){
        String var = ((EditText)findViewById(R.id.idEditTextSaisie)).getText().toString();
        Toast.makeText(getApplicationContext(), var, Toast.LENGTH_LONG).show();
    }
}
