# netty-app
netty-app

### 使用protobuf进行序列化与反序列化的操作

* 安装protobuf

    ```bash
    brew install protobuf@3.1
  
    ##查看安装成功
    protoc --version
    ```
    
* 定义proto文件

    ```proto
    // addressbook.proto
    syntax = "proto2";

    package tutorial;

    option java_package = "com.ibingbo.netty.app.protobuf";
    option java_outer_classname = "AddressBookProtos";

    message Person {
        required string name = 1;
        required int32 id = 2;
        optional string email = 3;

        enum PhoneType {
            MOBILE = 0;
            HOME = 1;
            WORK = 2;
        }

        message PhoneNumber {
            required string number = 1;
            optional PhoneType type = 2 [default = HOME];
        }

        repeated PhoneNumber phones = 4;
    }

    message AddressBook {
        repeated Person people = 1;
    }

    ```
    
* 生成对应的java类文件

    ```bash
    protoc --java_out=./netty-app-protobuf/src/main/java/ ./netty-app-protobuf/src/main/resources/proto/addressbook.proto
    ```
   
* 使用生成的java类

    ```java
    public class AddressBookProtosTest {
    
        public static void main(String[] args) {
            try {
    
    
                AddressBookProtos.Person.PhoneNumber phoneNumber = AddressBookProtos.Person.PhoneNumber.newBuilder()
                        .setNumber("11111111")
                        .setType(AddressBookProtos.Person.PhoneType.HOME)
                        .build();
    
                AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder()
                        .setId(1)
                        .setName("bill")
                        .setEmail("bill@gmail.com")
                        .addPhones(phoneNumber)
                        .build();
    
                AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder()
                        .addPeople(person)
                        .build();
    
                // 序列化
                byte[] bytes = addressBook.toByteArray();
                // 反序列化
                System.out.println(AddressBookProtos.AddressBook.parseFrom(bytes));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    ```
