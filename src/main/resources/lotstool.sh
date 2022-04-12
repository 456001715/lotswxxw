docker stop lots;
docker rm lots;
docker rmi lots;
cd /home/java/lots;
docker build -t lots .;
sleep 2s;
docker run --name lots -v /etc/localtime:/etc/localtime -v /etc/timezone:/etc/timezone -v /home/java/lots/logs/:/var/logs/ --restart=always -d -p 80:8080 lots;