(function ($) {
    const DB_VERSION = 3;
    const DB_NAME = 'eshopping';
    var myDB;

    $.fn.openIndexedDB = function () {
        return new Promise((resolve, reject) => {
            console.log("open db...");
            let request = indexedDB.open(DB_NAME, DB_VERSION);

            request.onsuccess = function (event) {
                myDB = event.target.result;
                console.log("open db success");
                resolve(true);
            }

            request.onupgradeneeded = function (event) {
                myDB = event.target.result;
                console.log("need to upgrade db...");
                clearDataTable(function () {
                    createTables();
                });
            }

            request.onerror = function () {
                reject(new Error('Open db fail'));
            }
        });
    }
    
    function createTables() {
        createTableShoppingCart();
    }

    function clearDataTable(callback) {
        var count = 0;
        var objectsStored = myDB.objectStoreNames;
        if(objectsStored.length > 0) {
            for(var i = 0; i < objectsStored.length; i++) {
                myDB.deleteObjectStore(objectsStored[i]);
                count++;
                if(count >= objectsStored.length) {
                    callback();
                }
            }
        } else {
            callback();
        }
    }
    
    function createTableShoppingCart() {
        var objectStore = myDB.createObjectStore("ShoppingCart", {keyPath: "id", autoIncrement: true});
        objectStore.createIndex("shoppingCartId", "shoppingCartId", {unique : true});
        objectStore.createIndex("skuDimensionCode", "skuDimensionCode", {unique : true});
        objectStore.createIndex("quantity", "quantity", {unique : false});
        objectStore.createIndex("skuDimension", "skuDimension", {unique : false});
        objectStore.createIndex("customer", "customer", {unique : false});
    }

    $.fn.saveObjectsToIndexedDB = function (objectName, objectArray) {
        return new Promise(function (resolve, reject) {
            if(objectArray) {
                let transaction = myDB.transaction(objectName, "readwrite");
                let objectStore = transaction.objectStore(objectName);
                let i = 0;
                while (i < objectArray.length) {
                    objectStore.put(objectArray[i]);
                    i++;
                }
                transaction.oncomplete = () => resolve(objectArray);
                transaction.onerror = (err) => reject(new Error(`Save object has been errored ${err}`));
            } else {
                reject(new Error('Items cannot null'));
            }
        });
    }

    $.fn.getObjectStoredWithIndex = function (objectName, indexName, param) {
        return new Promise(function (resolve, reject) {
            let transaction = myDB.transaction(objectName, "readonly");
            let objectStore = transaction.objectStore(objectName);
            let cursorRequest = objectStore.index(indexName).openCursor();

            cursorRequest.onsuccess = (event) => {
                let cursor = event.target.result;
                if(cursor) {
                    // shopping cart
                    let field = null;
                    if(objectName === 'ShoppingCart') {
                        field = cursor.value.skuDimensionCode;
                    }
                    if(field == param) {
                        resolve(cursor.value);
                    } else {
                        cursor.continue();
                    }
                } else {
                    resolve(null);
                }
            }

            cursorRequest.onerror = () => {
                reject(new Error(`cannot get object ${objectName}`));
            }
        });
    }

    $.fn.getAllObjectStored = function (objectName) {
        return new Promise(function (resolve, reject) {
            let result = [];
            let transaction = myDB.transaction(objectName, "readonly");
            let objectStore = transaction.objectStore(objectName);
            let cursorRequest = objectStore.openCursor();

            cursorRequest.onsuccess = function (ev) {
                var cursor = ev.target.result;
                if(cursor) {
                    result.push(cursor.value);
                    cursor.continue();
                }
            }
            transaction.oncomplete = () => {
                resolve(result);
            }

            transaction.onerror = () => {
                reject(new Error(`Cannot get all object ${objectName}`));
            }
        });
    }

    $.fn.updateObjectStored = function(objectName, objectValue) {
        return new Promise((resolve, reject) => {
            let transaction = myDB.transaction(objectName, "readwrite");
            let objectStore = transaction.objectStore(objectName);

            let requestUpdate = objectStore.put(objectValue);

            requestUpdate.onerror = (ev) => {
                reject(new Error(`Update object ${objectName} failure`));
            }

            transaction.oncomplete = (ev) => {
                resolve(true);
            }

        });
    }

})($);