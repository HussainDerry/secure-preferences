/*
 * Copyright 2017 Hussain Al-Derry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.hussainderry.securepreferences.util;

import android.content.SharedPreferences;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Helps get data from shared preferences asynchronously
 * @author Hussain Al-Derry
 */
public class AsyncDataLoader{

    private final SharedPreferences mPreferences;
    private final ExecutorService mExecutorService;

    public AsyncDataLoader(SharedPreferences preferences){
        if(preferences == null){
            throw new IllegalArgumentException("Param cannot be null!");
        }

        this.mPreferences = preferences;
        this.mExecutorService = Executors.newCachedThreadPool();
    }

    public Future<String> getString(final String key, final String defValue) {
        return mExecutorService.submit(new Callable<String>() {
            @Override
            public String call(){
                return mPreferences.getString(key, defValue);
            }
        });
    }

    public Future<Set<String>> getStringSet(final String key, final Set<String> defSet) {
        return mExecutorService.submit(new Callable<Set<String>>() {
            @Override
            public Set<String> call(){
                return mPreferences.getStringSet(key, defSet);
            }
        });
    }

    public Future<Integer> getInt(final String key, final Integer defValue) {
        return mExecutorService.submit(new Callable<Integer>() {
            @Override
            public Integer call(){
                return mPreferences.getInt(key, defValue);
            }
        });
    }

    public Future<Long> getLong(final String key, final Long defValue) {
        return mExecutorService.submit(new Callable<Long>() {
            @Override
            public Long call(){
                return mPreferences.getLong(key, defValue);
            }
        });
    }

    public Future<Float> getFloat(final String key, final Float defValue) {
        return mExecutorService.submit(new Callable<Float>() {
            @Override
            public Float call(){
                return mPreferences.getFloat(key, defValue);
            }
        });
    }

    public Future<Boolean> getBoolean(final String key, final Boolean defValue) {
        return mExecutorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call(){
                return mPreferences.getBoolean(key, defValue);
            }
        });
    }
}
