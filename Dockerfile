FROM php:8.3-apache

# Activer mod_rewrite
RUN a2enmod rewrite

# Installer dépendances système
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libpq-dev \
    && rm -rf /var/lib/apt/lists/*

# Extensions PHP nécessaires
RUN docker-php-ext-install pdo pdo_pgsql

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Copier le projet
WORKDIR /var/www/html
COPY . /var/www/html

# Installer uniquement les dépendances nécessaires en prod
RUN composer install --no-dev --prefer-dist --optimize-autoloader

# Nettoyer le cache et préparer les assets en mode prod
RUN php bin/console cache:clear --env=prod
RUN php bin/console assets:install public --env=prod

# Config Apache pour pointer vers /public
RUN sed -i 's|/var/www/html|/var/www/html/public|g' /etc/apache2/sites-available/000-default.conf
RUN sed -i 's|DocumentRoot /var/www/html|DocumentRoot /var/www/html/public|g' /etc/apache2/sites-available/000-default.conf

# Droits sur le dossier var
RUN chown -R www-data:www-data /var/www/html/var

# Variables d'environnement
ENV APP_ENV=prod
ENV APP_DEBUG=0

EXPOSE 80

CMD ["apache2-foreground"]
