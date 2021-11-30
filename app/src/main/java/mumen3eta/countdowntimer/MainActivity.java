package mumen3eta.countdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Button button;
    MediaPlayer mediaPlayer;
    boolean counterActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        mediaPlayer = MediaPlayer.create(this, R.raw.horn);


        seekBar.setMax(300);
        seekBar.setProgress(150);
        textView.setText(String.valueOf(seekBar.getProgress()/60) + ":" + String.valueOf(seekBar.getProgress()%60));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int minutes = progress / 60;
                int seconds = progress % 60;
                String secondsString = Integer.toString(seconds);
                if (secondsString.length() == 1) secondsString = "0" + secondsString;

                textView.setText(String.valueOf(minutes) + ":" + secondsString);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counterActive){
                    countDownTimer.cancel();
                    seekBar.setEnabled(true);
                    counterActive = false;
                    seekBar.setProgress(150);
                    button.setText("GO");
                    textView.setText(String.valueOf(seekBar.getProgress()/60) + ":" + String.valueOf(seekBar.getProgress()%60));
                }else {
                    counterActive = true;
                    seekBar.setEnabled(false);
                    button.setText("STOP");
                    int time = seekBar.getProgress() * 1000;
                    countDownTimer =  new CountDownTimer(time + 100, 1000){

                        @Override
                        public void onTick(long l) {
                            int time = (int) l/1000;
                            int minutes = time / 60;
                            int seconds = time % 60;
                            String secondsString = Integer.toString(seconds);
                            if (secondsString.length() == 1) secondsString = "0" + secondsString;

                            textView.setText(String.valueOf(minutes) + ":" + secondsString);
                        }

                        @Override
                        public void onFinish() {
                            mediaPlayer.start();
                            button.setText("GO");
                            seekBar.setEnabled(true);
                            seekBar.setProgress(150);
                            counterActive = false;
                            textView.setText(String.valueOf(seekBar.getProgress()/60) + ":" + String.valueOf(seekBar.getProgress()%60));
                        }
                    }.start();
                }

            }
        });

    }


}