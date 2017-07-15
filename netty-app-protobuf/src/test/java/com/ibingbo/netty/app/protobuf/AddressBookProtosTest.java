package com.ibingbo.netty.app.protobuf;

/**
 * Created by bing on 17/7/15.
 */
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
