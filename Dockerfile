FROM --platform=linux/amd64 ubuntu:latest

RUN apt-get update && apt-get install -y \
    wget \
    gnupg2 \
    software-properties-common \
    unzip \
    zip \
    xvfb \
    firefox \
    curl

RUN add-apt-repository ppa:openjdk-r/ppa

RUN apt-get update; apt-get clean

RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list

RUN apt-get update && apt-get -y install google-chrome-stable

RUN apt-get install -y openjdk-21-jdk

RUN curl -s "https://get.sdkman.io" | bash \
    && bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install kotlin"

COPY . /app
WORKDIR /app

RUN ./gradlew build

ENV DISPLAY=:99

CMD Xvfb :99 -screen 0 1280x1024x24 & java -jar /app/build/libs/ParseServer-0.0.1-SNAPSHOT.jar

