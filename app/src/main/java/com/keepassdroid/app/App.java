/*
 * Copyright 2009-2013 Brian Pellin.
 *     
 * This file is part of KeePassDroid.
 *
 *  KeePassDroid is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  KeePassDroid is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.keepassdroid.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.keepassdroid.Database;
import com.keepassdroid.compat.PRNGFixes;
import com.keepassdroid.fileselect.RecentFileHistory;
import com.keepassdroid.utils.AESUtils;

import org.apache.commons.lang3.StringUtils;
import org.spongycastle.util.encoders.Hex;

import java.util.Calendar;

public class App extends Application {
	private static Database db = null;
	private static boolean shutdown = false;
	private static Calendar calendar = null;
	private static RecentFileHistory fileHistory = null;

	public static App INSTANCE =  null;
	
	public static Database getDB() {
		if ( db == null ) {
			db = new Database();
		}
		
		return db;
	}
	
	public static RecentFileHistory getFileHistory() {
		return fileHistory;
	}
	
	public static void setDB(Database d) {
		db = d;
	}
	
	public static boolean isShutdown() {
		return shutdown;
	}
	
	public static void setShutdown() {
		shutdown = true;
	}
	
	public static void clearShutdown() {
		shutdown = false;
	}
	
	public static Calendar getCalendar() {
		if ( calendar == null ) {
			calendar = Calendar.getInstance();
		}
		
		return calendar;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		INSTANCE = this;
		fileHistory = new RecentFileHistory(this);
		
		PRNGFixes.apply();
	}

	@Override
	public void onTerminate() {
		if ( db != null ) {
			db.clear();
		}
		INSTANCE = null;
		super.onTerminate();
	}

	static final byte[] bb={81,81,109,48,57,119,99,69,65,72,69,97,115,102,102};

	public static String getSavedPassword(Context ctx) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		String original = prefs.getString("_savedpassword", "");
		if (StringUtils.isEmpty(original)){
			return StringUtils.EMPTY;
		}
		try {
			byte[] bytes = Hex.decode(original);
			byte[] decrypt = AESUtils.decrypt(bytes, bb);
			return new String(decrypt,"UTF-8");
		} catch (Exception e) {
		}
		return StringUtils.EMPTY;
	}

	public static void setSavedPassword(Context ctx,String p) {
		try {
			byte[] aaa = p.getBytes("utf-8");
			byte[] encrypt = AESUtils.encrypt(aaa, bb);
			p = Hex.toHexString(encrypt);
		} catch (Throwable e) {
			p=StringUtils.EMPTY;
			e.printStackTrace();
		}

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("_savedpassword", p);
		edit.commit();
	}
}
