FROM php:8.3-apache

# 1. Activer mod_rewrite
RUN a2enmod rewrite

# 2. Installer dépendances système
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libpq-dev \
    && rm -rf /var/lib/apt/lists/*

# 3. Extensions PHP nécessaires
RUN docker-php-ext-install pdo pdo_pgsql

# 4. Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# 5. CONFIGURATION ENVIRONNEMENT (Important : Placé AVANT le build)
WORKDIR /var/www/html
ENV APP_ENV=prod
ENV APP_DEBUG=0
# Corrige l'erreur de "dubious ownership" vue dans vos logs
RUN git config --global --add safe.directory /var/www/html

# 6. Copier le projet
COPY . /var/www/html

# 7. Installer les dépendances (Maintenant Symfony sait qu'il est en PROD)
RUN composer install --no-dev --prefer-dist --optimize-autoloader

# 8. Nettoyer le cache et préparer les assets
RUN php bin/console cache:clear --env=prod
RUN php bin/console assets:install public --env=prod

# 9. Config Apache pour pointer vers /public
RUN sed -i 's|/var/www/html|/var/www/html/public|g' /etc/apache2/sites-available/000-default.conf
RUN sed -i 's|DocumentRoot /var/www/html|DocumentRoot /var/www/html/public|g' /etc/apache2/sites-available/000-default.conf

# 10. Droits sur le dossier var
RUN chown -R www-data:www-data /var/www/html/var

EXPOSE 80

CMD ["apache2-foreground"]