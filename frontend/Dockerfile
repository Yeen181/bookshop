FROM node:14.21.3-bullseye as build

WORKDIR /usr/local/app
# RUN npm i -g @angular/cli@14.0.6

COPY ["package.json", "./"]

RUN npm install

COPY ./ /usr/local/app/

# RUN ng build --output-path=dist/vuexy
RUN npm run build

FROM nginx:latest

COPY /nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/local/app/dist/vuexy /usr/share/nginx/html
# RUN rm /usr/share/nginx/html/index.html
# RUN cp /usr/share/nginx/html/ra/index.html /usr/share/nginx/html/index.html
EXPOSE 80
