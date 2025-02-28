#!/bin/bash
function build_frame() {
    echo 'start build frame'
    cd ./truntao-backend
    mvn clean package
    cd ..
    docker-compose build truntao-frame
    docker-compose up -d truntao-frame
}

function build_frame_ui() {
    echo 'start build web ui'
    cd ./truntao-vue3
    npm install --legacy-peer-deps --registry=https://registry.npmmirror.com && npm run build:stage || exit 111
    docker-compose build truntao-frame-ui
    docker-compose up -d truntao-frame-ui
}

function build_all() {
    echo 'start build all'
}


echo $module_name
if [ "$module_name" = "frame-backend" ]; then
    build_frame
elif [ "$module_name" = "frame-ui" ]; then
    build_frame_ui
else 
    build_all
fi
