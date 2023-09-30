package gugunava.danil.usermanagementservice.cache;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import gugunava.danil.usermanagementservice.model.User;

import java.io.IOException;

public class UserSerializer implements StreamSerializer<User> {

    @Override
    public void write(ObjectDataOutput out, User object) throws IOException {
        out.writeLong(object.getId());
        out.writeString(object.getUserName());
        out.writeString(object.getEmail());
    }

    @Override
    public User read(ObjectDataInput in) throws IOException {
        return new User(
                in.readLong(),
                in.readString(),
                in.readString()
        );
    }

    @Override
    public int getTypeId() {
        return 1;
    }
}
