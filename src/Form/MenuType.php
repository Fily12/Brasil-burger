<?php

namespace App\Form;

use App\Entity\Burger;
use App\Entity\Complement;
use App\Entity\Menu;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Validator\Constraints\NotBlank;

/**
 * Formulaire de gestion des menus
 * 
 * Ce formulaire permet de créer ou modifier un menu en sélectionnant :
 * - Le nom du menu
 * - L'image du menu
 * - Les burgers inclus dans le menu
 * - Les compléments inclus dans le menu (frites, boissons)
 * 
 * @author Étudiant 3ème année - Brasil Burger
 */
class MenuType extends AbstractType
{
    /**
     * Construction du formulaire
     * 
     * @param FormBuilderInterface $builder Constructeur de formulaire Symfony
     * @param array $options Options du formulaire
     */
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            // Champ : Nom du menu
            ->add('nom', TextType::class, [
                'label' => 'Nom du menu',
                'attr' => [
                    'placeholder' => 'Ex: Menu Brasil Classic',
                    'class' => 'form-control'
                ],
                'constraints' => [
                    new NotBlank([
                        'message' => 'Le nom du menu est obligatoire.',
                    ]),
                ],
            ])

            // Champ : Image du menu (upload de fichier)
            ->add('imageFile', FileType::class, [
                'label' => 'Image du menu',
                'mapped' => false, // Ce champ n'est pas directement lié à l'entité
                'required' => false, // Optionnel en modification
                'attr' => [
                    'accept' => 'image/*',
                    'class' => 'form-control'
                ],
                'help' => 'Formats acceptés : JPG, PNG. Nom recommandé : menu_xxx.jpg',
                'constraints' => [
                    new File([
                        'maxSize' => '2M',
                        'mimeTypes' => [
                            'image/jpeg',
                            'image/png',
                            'image/jpg',
                        ],
                        'mimeTypesMessage' => 'Veuillez uploader une image valide (JPG, PNG).',
                    ])
                ],
            ])

            // Champ : Sélection des burgers (choix multiple)
            ->add('burgers', EntityType::class, [
                'class' => Burger::class,
                'choice_label' => 'nom', // Affiche le nom du burger
                'multiple' => true, // Sélection multiple
                'expanded' => true, // Affichage en checkboxes
                'label' => 'Burgers inclus dans le menu',
                'attr' => [
                    'class' => 'burger-selection'
                ],
                'query_builder' => function ($repository) {
                    // Récupère uniquement les burgers NON archivés
                    return $repository->createQueryBuilder('b')
                        ->where('b.archive = :archive')
                        ->setParameter('archive', false)
                        ->orderBy('b.nom', 'ASC');
                },
                'constraints' => [
                    new NotBlank([
                        'message' => 'Vous devez sélectionner au moins un burger.',
                    ]),
                ],
            ])

            // Champ : Sélection des compléments (choix multiple)
            ->add('complements', EntityType::class, [
                'class' => Complement::class,
                'choice_label' => 'nom', // Affiche le nom du complément
                'multiple' => true, // Sélection multiple
                'expanded' => true, // Affichage en checkboxes
                'label' => 'Compléments inclus dans le menu (frites, boissons)',
                'attr' => [
                    'class' => 'complement-selection'
                ],
                'query_builder' => function ($repository) {
                    // Récupère uniquement les compléments NON archivés
                    return $repository->createQueryBuilder('c')
                        ->where('c.archive = :archive')
                        ->setParameter('archive', false)
                        ->orderBy('c.nom', 'ASC');
                },
                'constraints' => [
                    new NotBlank([
                        'message' => 'Vous devez sélectionner au moins un complément.',
                    ]),
                ],
            ]);
    }

    /**
     * Configuration des options du formulaire
     * 
     * @param OptionsResolver $resolver Résolveur d'options Symfony
     */
    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Menu::class, // Le formulaire est lié à l'entité Menu
        ]);
    }
}