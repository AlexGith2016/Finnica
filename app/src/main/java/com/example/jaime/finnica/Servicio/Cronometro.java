package com.example.jaime.finnica.Servicio;

/**
 * Created by Carlos Tamariz on 08/12/2016.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.jaime.finnica.DeudasActivity;
import com.example.jaime.finnica.MainActivity;
import com.example.jaime.finnica.clases.Prestamo;


public class Cronometro extends Service {

    private Timer temporizador = new Timer();
    private static final long INTERVALO_ACTUALIZACION = 1000*60; // En 60000
    public static MainActivity UPDATE_LISTENER;
    private double cronometro = 0;
    int i = 0;
    private Handler handler;
    List<Prestamo>deudas;
    String agenteDeuda;
    String infoDeuda;


    /**
     * Establece quien va ha recibir las actualizaciones del cronometro
     *
     * @param poiService
     */
    public static void setUpdateListener(MainActivity poiService) {

        UPDATE_LISTENER = poiService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Toast.makeText(this, "llego al cronometro:  ", Toast.LENGTH_SHORT).show();

        iniciarCronometro();


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                UPDATE_LISTENER.actualizarCronometro(cronometro);
                //Verificar si se cumple la fecha para notificar
            }
        };
    }

    @Override
    public void onDestroy() {
        //  pararCronometro();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void iniciarCronometro() {
        temporizador.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                cronometro += 3;
                agregar();
                compararFecha();
              //  Notificar(cronometro+" ");


                if(UPDATE_LISTENER != null) {
                    handler.sendEmptyMessage(0);
                }
            }
        }, 0, INTERVALO_ACTUALIZACION);
    }

    private void pararCronometro() {
        if (temporizador != null)
            temporizador.cancel();
    }

    public void Notificar(String numero)
    {
        final String number= numero;
        UPDATE_LISTENER.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(UPDATE_LISTENER, " "+number, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void agregar(){
        deudas=Prestamo.listAll(Prestamo.class);
       // Notificar("las cargo al arreglo "+deudas.size());

    }



    public  void notificar(){
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Notificación Deuda")
                        .setContentText("Cancelar la cuota: "+this.agenteDeuda+"\n "+this.infoDeuda);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(UPDATE_LISTENER, DeudasActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }


    public Calendar obtenerFechaAndroid(){
        Calendar fechaAndroid = Calendar.getInstance();
        return fechaAndroid;
    }

    public int compareTwoDate(int dA, int mA, int aA, int dD, int mD, int aD){
        if(dA==dD && mA==mD && aA==aD){
            return 0;//fechas iguales
        }
        if(dA==dD && mA>mD && aA==aD){
            return 1;
        }
        if(dA==dD  && aA>=aD){
            return 2;
        }

        return -1;
    }

    public Calendar convertirDateToCalendar(Date fechaDeuda){
        Calendar c=Calendar.getInstance();
        c.setTime(fechaDeuda);
        return c;
    }
    public int compararFecha(){
       // Notificar("entro al metodo");

        int encontrado=0;
        Date fechad;//fecha deuda

        Calendar fechaA=obtenerFechaAndroid();
        //Notificar("Buen Pedorro"+fechaA.getDay());


        int dia = fechaA.get(Calendar.DAY_OF_MONTH);

        int dA=dia;
        int mA=fechaA.get(Calendar.MONTH)+1;
        int aA=fechaA.get(Calendar.YEAR);



       // Notificar("antes del for: "+deudas.size());
        for(int i = 0; i<deudas.size();i++)
        {

            Prestamo e = deudas.get(i);
            Alerta("e.getUltimoMes() "+e.getUltimoMes());
           // Notificar("entro al for");
            //fecha de la deuda

            // Calendar cal = Calendar.getInstance();
            //cal.setTime(e.getFechaInicio());
            Calendar cal=convertirDateToCalendar(e.getFecha());
            int d = cal.get(Calendar.DAY_OF_MONTH);

           // Notificar("El día de la deuda es "+d);
            int m=cal.get(Calendar.MONTH)+1;
            int a=cal.get(Calendar.YEAR);

            Alerta("Numero de cuotas canceladas "+e.getNcuoCan()+ "  Numero de cuotas "+e.getnCuotas());


            if(d==dA && e.getNcuoCan()<=e.getnCuotas()){
                Alerta("Pasa por el primer if");
                Alerta("deuda "+d+" android"+dA);
                Alerta("antes de comparar dos date");
                int f=compareTwoDate(dA,mA,aA,d,m,a);
                Alerta("f= "+f);
                if(f==0)
                {

                    Alerta("es el mismo dia, no se notifica");
                }
                Alerta("ultimoMes"+e.getUltimoMes());
                if(mA>e.getUltimoMes()||(mA<=e.getUltimoMes() && aA>a))
                {
                    Alerta("antes del setNotiificado");


                    if((f==1 || f==2) && e.getNotificado()==0){
                        Alerta("antes de notificar");
                        this.agenteDeuda=e.getAgenteFinanciero().toString();
                        this.infoDeuda="cuota: "+e.getNcuoCan()+" de: "+e.getnCuotas();
                        notificar();

                        e.setNotificado(1);
                        e.setUltimoMes(mA);//se guarda el ultimo mes que se notifica
                        e.setNcuoCan(e.getNcuoCan()+1);
                        e.save();

                        Alerta("numero cuotas candeladas "+e.getNcuoCan());
                        return 1;
                    }
                }



            }
            else
            {
                Alerta("Pasa por el ultimo ELse");
                if(dA!=d){
                    e.setNotificado(0);
                    Alerta("el dia es distinto");
                }
                Alerta("numero cuotas candeladas "+e.getNcuoCan());
            }
        }
        return encontrado;
    }
    private void Alerta(String mensaje) {
        Log.i("Información", mensaje);
    }

}
