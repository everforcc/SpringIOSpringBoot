<span  style="font-family: Simsun,serif; font-size: 17px; ">

~~~

 public V get(Object key) {
        if (key == null) {
            HashMapEntry<K, V> e = entryForNullKey;
            return e == null ? null : e.value;
        }

        int hash = Collections.secondaryHash(key);
        HashMapEntry<K, V>[] tab = table;
        for (HashMapEntry<K, V> e = tab[hash & (tab.length - 1)];
                e != null; e = e.next) {
            K eKey = e.key;
            if (eKey == key || (e.hash == hash && key.equals(eKey))) {
                return e.value;
            }
        }
        return null;
    }

int hash = Collections.secondaryHash(key); 计算出散列码
HashMapEntry
            K eKey = e.key;
            if (eKey == key || (e.hash == hash && key.equals(eKey))) {
                return e.value;
            }

若地址相同，返回值，
若hash值相等且equals返回true，返回值
~~~

</span>