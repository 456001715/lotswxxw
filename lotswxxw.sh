docker stop lotswxxw
docker rm lotswxxw
docker rmi lotswxxw
cd /home/java/lotswxxw
docker build -t lotswxxw .
sleep 6s
docker run --name lotswxxw -v /etc/localtime:/etc/localtime -v /etc/timezone:/etc/timezone --restart=always -d -p 80:8080 lotswxxw