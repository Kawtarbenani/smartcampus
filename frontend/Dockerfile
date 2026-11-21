# Étape 1 : Build de l'application React
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

<<<<<<< HEAD
# Étape 2 : Serveur web Nginx pour servir les fichiers
=======
# Étape 2 : Serveur web Nginx
>>>>>>> 33e2329b2c4fc00066e32e6d0a7af63567419ca9
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]