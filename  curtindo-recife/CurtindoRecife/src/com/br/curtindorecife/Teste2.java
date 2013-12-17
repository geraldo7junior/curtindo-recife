package com.br.curtindorecife;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

//MainActivity herda de ActivityGroup, porque voc� est� lidando
//com v�rias atividades em um elemento s�. Ou seja, o TabHost hospeda um grupo de atividades.
public class Teste2 extends ActivityGroup {
static TabHost tabHost;
static int tab = -1;

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_teste2);

Resources res = getResources();
tabHost = (TabHost)findViewById(R.id.tabhost);
tabHost.setup(this.getLocalActivityManager());
TabHost.TabSpec spec;
Intent intent;

//Cada Tab adicionada tem sua pr�pria Activity, que por sua vez , tem seu pr�prio .xml(layout) e //tamb�m tem um elemento Drawable. Esse elemento eu usei para mudar o �cone da Tab.
//Quando ela n�o est� ativa, o �cone � cinza. Quando ela est� ativa, o �cone fica colorido e indica //pro usu�rio que ele est� naquela Tab.

//Adiciona Tab #1
intent = new Intent().setClass(this, TelaPrincipal.class);
spec = tabHost.newTabSpec("0").setIndicator("Home").setContent(intent);
tabHost.addTab(spec);

//Adiciona Tab #2
intent = new Intent(this, TelaEventos.class);
spec = tabHost.newTabSpec("1").setIndicator("Cidade").setContent(intent);
tabHost.addTab(spec);

tabHost.setCurrentTab(0);

}

}