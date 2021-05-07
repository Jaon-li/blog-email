mvn clean package
scp target/blog-email-0.0.1-SNAPSHOT.jar tx:/data/server/blog/email
ssh tx "cd /data/server/blog/email;source /etc/profile;sh start.sh"

