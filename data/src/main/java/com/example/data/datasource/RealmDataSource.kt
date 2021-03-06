package com.example.data.datasource

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.RealmResults

internal abstract class RealmDataSource<T: RealmObject>(private val clazz: Class<T>) {

    fun getRealmInstance(): Realm {
        val config = RealmConfiguration.Builder()
            .name("dataRealm.realm")
            .schemaVersion(1)
            .build()
        return Realm.getInstance(config)
    }

    fun saveRates(input: T) {
        getRealmInstance().use { realm ->
            realm.executeTransaction {
                it.insertOrUpdate(input)
            }
        }
    }

    fun getRates(): RealmResults<T> {
        return getRealmInstance().use { realm ->
            realm.where(clazz).findAll()
        }
    }

}