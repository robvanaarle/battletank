FROM java:7-jdk

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN javac -d build /usr/src/app/src/battletank/*.java \
        /usr/src/app/src/battletank/math/*.java \
        /usr/src/app/src/battletank/objects/*.java \
        /usr/src/app/src/battletank/tankai/*.java

WORKDIR /usr/src/app/build

CMD ["java", "battletank/Battletank"]