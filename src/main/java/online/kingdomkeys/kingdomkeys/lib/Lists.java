package online.kingdomkeys.kingdomkeys.lib;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

import static online.kingdomkeys.kingdomkeys.item.ModItems.*;

public class Lists {

    public static List<String> recipes = new ArrayList<String>();

    public static List<Item> Xemnas = new ArrayList<Item>();
    public static List<Item> Xigbar = new ArrayList<Item>();
    public static List<Item> Xaldin = new ArrayList<Item>();
    public static List<Item> Vexen = new ArrayList<Item>();
    public static List<Item> Lexaeus = new ArrayList<Item>();
    public static List<Item> Zexion = new ArrayList<Item>();
    public static List<Item> Saix = new ArrayList<Item>();
    public static List<Item> Axel = new ArrayList<Item>();
    public static List<Item> Demyx = new ArrayList<Item>();
    public static List<Item> Luxord = new ArrayList<Item>();
    public static List<Item> Marluxia = new ArrayList<Item>();
    public static List<Item> Larxene = new ArrayList<Item>();
    public static List<Item> Roxas = new ArrayList<>();

    public static List<Item> getListForMember(Utils.OrgMember member) {
        switch (member) {
            case AXEL: return Axel;
            case DEMYX: return Demyx;
            case LARXENE: return Larxene;
            case LEXAEUS: return Lexaeus;
            case LUXORD: return Luxord;
            case MARLUXIA: return Marluxia;
            case ROXAS: return Roxas;
            case SAIX: return Saix;
            case VEXEN: return Vexen;
            case XALDIN: return Xaldin;
            case XEMNAS: return Xemnas;
            case XIGBAR: return Xigbar;
            case ZEXION: return Zexion;
            case NONE: return null;
        }
        return null;
    }

    public static void init () {

        //Xemnas
        Xemnas.add(malice);
        Xemnas.add(sanction);
        Xemnas.add(overlord);
        Xemnas.add(veneration);
        Xemnas.add(autocracy);
        Xemnas.add(conquest);
        Xemnas.add(terminus);
        Xemnas.add(judgement);
        Xemnas.add(discipline);
        Xemnas.add(aristocracy);
        Xemnas.add(superiority);
        Xemnas.add(aggression);
        Xemnas.add(fury);
        Xemnas.add(despair);
        Xemnas.add(triumph);
        Xemnas.add(ruination);
        Xemnas.add(domination);
        Xemnas.add(annihilation);
        Xemnas.add(tyrant);
        Xemnas.add(magnificence);
        Xemnas.add(infinity);
        Xemnas.add(interdiction);
        Xemnas.add(roundFan);
        Xemnas.add(absolute);

        //Xigbar
        Xigbar.add(standalone);
        Xigbar.add(killerbee);
        Xigbar.add(stingray);
        Xigbar.add(counterweight);
        Xigbar.add(precision);
        Xigbar.add(dualHead);
        Xigbar.add(bahamut);
        Xigbar.add(gullwing);
        Xigbar.add(blueFrame);
        Xigbar.add(starShell);
        Xigbar.add(sunrise);
        Xigbar.add(ignition);
        Xigbar.add(armstrong);
        Xigbar.add(hardBoiledHeat);
        Xigbar.add(diabloEye);
        Xigbar.add(doubleTap);
        Xigbar.add(stardust);
        Xigbar.add(energyMuzzle);
        Xigbar.add(crimeAndPunishment);
        Xigbar.add(cupidsArrow);
        Xigbar.add(finalWeapon);
        Xigbar.add(sharpshooter);
        Xigbar.add(dryer);
        Xigbar.add(trumpet);

        //Xaldin
        Xaldin.add(zephyr);
        Xaldin.add(moonglade);
        Xaldin.add(aer);
        Xaldin.add(nescience);
        Xaldin.add(brume);
        Xaldin.add(asura);
        Xaldin.add(crux);
        Xaldin.add(paladin);
        Xaldin.add(fellking);
        Xaldin.add(nightcloud);
        Xaldin.add(shimmer);
        Xaldin.add(vortex);
        Xaldin.add(scission);
        Xaldin.add(heavenfall);
        Xaldin.add(aether);
        Xaldin.add(mazzaroth);
        Xaldin.add(hegemon);
        Xaldin.add(foxfire);
        Xaldin.add(yaksha);
        Xaldin.add(cynosura);
        Xaldin.add(dragonreign);
        Xaldin.add(lindworm);
        Xaldin.add(broom);
        Xaldin.add(wyvern);

        //Vexen
        Vexen.add(testerZero);
        Vexen.add(productOne);
        Vexen.add(deepFreeze);
        Vexen.add(cryoliteShield);
        Vexen.add(falseTheory);
        Vexen.add(glacier);
        Vexen.add(absoluteZero);
        Vexen.add(gunz);
        Vexen.add(mindel);
        Vexen.add(snowslide);
        Vexen.add(iceberg);
        Vexen.add(inquisition);
        Vexen.add(scrutiny);
        Vexen.add(empiricism);
        Vexen.add(edification);
        Vexen.add(contrivance);
        Vexen.add(wurm);
        Vexen.add(subzero);
        Vexen.add(coldBlood);
        Vexen.add(diamondShield);
        Vexen.add(aegis);
        Vexen.add(frozenPride);
        Vexen.add(potLid);
        Vexen.add(snowman);

        //Lexaeus
        Lexaeus.add(reticence);
        Lexaeus.add(goliath);
        Lexaeus.add(copperRed);
        Lexaeus.add(daybreak);
        Lexaeus.add(colossus);
        Lexaeus.add(ursaMajor);
        Lexaeus.add(megacosm);
        Lexaeus.add(terrene);
        Lexaeus.add(fuligin);
        Lexaeus.add(hardWinter);
        Lexaeus.add(firefly);
        Lexaeus.add(harbinger);
        Lexaeus.add(redwood);
        Lexaeus.add(sequoia);
        Lexaeus.add(ironBlack);
        Lexaeus.add(earthshine);
        Lexaeus.add(octiron);
        Lexaeus.add(hyperion);
        Lexaeus.add(clarity);
        Lexaeus.add(oneThousandAndOneNights);
        Lexaeus.add(cardinalVirtue);
        Lexaeus.add(skysplitter);
        Lexaeus.add(bleepBloopBop);
        Lexaeus.add(monolith);

        //Zexion
        Zexion.add(blackPrimer);
        Zexion.add(whiteTome);
        Zexion.add(illicitResearch);
        Zexion.add(buriedSecrets);
        Zexion.add(arcaneCompendium);
        Zexion.add(dissentersNotes);
        Zexion.add(nefariousCodex);
        Zexion.add(mysticAlbum);
        Zexion.add(cursedManual);
        Zexion.add(tabooText);
        Zexion.add(eldritchEsoterica);
        Zexion.add(freakishBestiary);
        Zexion.add(madmansVita);
        Zexion.add(untitledWritings);
        Zexion.add(abandonedDogma);
        Zexion.add(atlasOfOmens);
        Zexion.add(revoltingScrapbook);
        Zexion.add(lostHeterodoxy);
        Zexion.add(otherworldlyTales);
        Zexion.add(indescribableLore);
        Zexion.add(radicalTreatise);
        Zexion.add(bookOfRetribution);
        Zexion.add(midnightSnack);
        Zexion.add(dearDiary);

        //Saix
        Saix.add(newMoon);
        Saix.add(werewolf);
        Saix.add(artemis);
        Saix.add(luminary);
        Saix.add(selene);
        Saix.add(moonrise);
        Saix.add(astrologia);
        Saix.add(crater);
        Saix.add(lunarPhase);
        Saix.add(crescent);
        Saix.add(gibbous);
        Saix.add(berserker);
        Saix.add(twilight);
        Saix.add(queenOfTheNight);
        Saix.add(balsamicMoon);
        Saix.add(orbit);
        Saix.add(lightYear);
        Saix.add(kingOfTheNight);
        Saix.add(moonset);
        Saix.add(horoscope);
        Saix.add(dichotomy);
        Saix.add(lunatic);
        Saix.add(justDesserts);
        Saix.add(bunnymoon);

        //Axel
        Axel.add(ashes);
        Axel.add(doldrums);
        Axel.add(delayedAction);
        Axel.add(diveBombers);
        Axel.add(combustion);
        Axel.add(moulinRouge);
        Axel.add(blazeOfGlory);
        Axel.add(prometheus);
        Axel.add(ifrit);
        Axel.add(magmaOcean);
        Axel.add(volcanics);
        Axel.add(inferno);
        Axel.add(sizzlingEdge);
        Axel.add(corona);
        Axel.add(ferrisWheel);
        Axel.add(burnout);
        Axel.add(omegaTrinity);
        Axel.add(outbreak);
        Axel.add(doubleEdge);
        Axel.add(wildfire);
        Axel.add(prominence);
        Axel.add(eternalFlames);
        Axel.add(pizzaCut);
        Axel.add(conformers);

        //Demyx
        Demyx.add(basicModel);
        Demyx.add(tuneUp);
        Demyx.add(quartet);
        Demyx.add(quintet);
        Demyx.add(overture);
        Demyx.add(oldHand);
        Demyx.add(daCapo);
        Demyx.add(powerChord);
        Demyx.add(fermata);
        Demyx.add(interlude);
        Demyx.add(serenade);
        Demyx.add(songbird);
        Demyx.add(riseToFame);
        Demyx.add(rockStar);
        Demyx.add(eightFinger);
        Demyx.add(concerto);
        Demyx.add(harmonics);
        Demyx.add(millionBucks);
        Demyx.add(fortissimo);
        Demyx.add(upToEleven);
        Demyx.add(sanctuary);
        Demyx.add(arpeggio);
        Demyx.add(princeOfAwesome);
        Demyx.add(afterSchool);

        //Luxord
        Luxord.add(theFool);
        Luxord.add(theMagician);
        Luxord.add(theStar);
        Luxord.add(theMoon);
        Luxord.add(justice);
        Luxord.add(theHierophant);
        Luxord.add(theWorld);
        Luxord.add(temperance);
        Luxord.add(theHighPriestess);
        Luxord.add(theTower);
        Luxord.add(theHangedMan);
        Luxord.add(death);
        Luxord.add(theHermit);
        Luxord.add(strength);
        Luxord.add(theLovers);
        Luxord.add(theChariot);
        Luxord.add(theSun);
        Luxord.add(theDevil);
        Luxord.add(theEmpress);
        Luxord.add(theEmperor);
        Luxord.add(theJoker);
        Luxord.add(fairGame);
        Luxord.add(finestFantasy13);
        Luxord.add(highRollersSecret);

        //Marluxia
        Marluxia.add(fickleErica);
        Marluxia.add(jiltedAnemone);
        Marluxia.add(proudAmaryllis);
        Marluxia.add(madSafflower);
        Marluxia.add(poorMelissa);
        Marluxia.add(tragicAllium);
        Marluxia.add(mournfulCineria);
        Marluxia.add(pseudoSilene);
        Marluxia.add(faithlessDigitalis);
        Marluxia.add(grimMuscari);
        Marluxia.add(docileVallota);
        Marluxia.add(quietBelladonna);
        Marluxia.add(partingIpheion);
        Marluxia.add(loftyGerbera);
        Marluxia.add(gallantAchillea);
        Marluxia.add(noblePeony);
        Marluxia.add(fearsomeAnise);
        Marluxia.add(vindictiveThistle);
        Marluxia.add(fairHelianthus);
        Marluxia.add(solemnMagnolia);
        Marluxia.add(hallowedLotus);
        Marluxia.add(gracefulDahlia);
        Marluxia.add(stirringLadle);
        Marluxia.add(daintyBellflowers);

        //Larxene
        Larxene.add(trancheuse);
        Larxene.add(orage);
        Larxene.add(tourbillon);
        Larxene.add(tempete);
        Larxene.add(carmin);
        Larxene.add(meteore);
        Larxene.add(etoile);
        Larxene.add(irregulier);
        Larxene.add(dissonance);
        Larxene.add(eruption);
        Larxene.add(soleilCouchant);
        Larxene.add(indigo);
        Larxene.add(vague);
        Larxene.add(deluge);
        Larxene.add(rafale);
        Larxene.add(typhon);
        Larxene.add(extirpeur);
        Larxene.add(croixDuSud);
        Larxene.add(lumineuse);
        Larxene.add(clairDeLune);
        Larxene.add(volDeNuit);
        Larxene.add(foudre);
        Larxene.add(demoiselle);
        Larxene.add(ampoule);

        Roxas.add(kingdomKey.get());
        Roxas.add(missingAche.get());
        Roxas.add(ominousBlight.get());
        Roxas.add(abaddonPlasma.get());
        Roxas.add(painOfSolitude.get());
        Roxas.add(signOfInnocence.get());
        Roxas.add(crownOfGuilt.get());
        Roxas.add(abyssalTide.get());
        Roxas.add(leviathan.get());
        Roxas.add(trueLightsFlight.get());
        Roxas.add(rejectionOfFate.get());
        Roxas.add(midnightRoar.get());
        Roxas.add(glimpseOfDarkness.get());
        Roxas.add(totalEclipse.get());
        Roxas.add(silentDirge.get());
        Roxas.add(lunarEclipse.get());
        Roxas.add(darkerThanDark.get());
        Roxas.add(astralBlast.get());
        Roxas.add(maverickFlare.get());
        Roxas.add(twilightBlaze.get());
        Roxas.add(omegaWeapon.get());
        Roxas.add(oathkeeper.get());
        Roxas.add(twoBecomeOne.get());
        Roxas.add(oblivion.get());
        Roxas.add(umbrella.get());
        Roxas.add(aubade.get());
        Roxas.add(woodenStick.get());

        //Keyblades
        recipes.add(abaddonPlasma.get().getTranslationKey());
        recipes.add(abyssalTide.get().getTranslationKey());
        recipes.add(allForOne.get().getTranslationKey());
        recipes.add(invisKeyblade.get().getTranslationKey());
        recipes.add(astralBlast.get().getTranslationKey());
        recipes.add(aubade.get().getTranslationKey());
        recipes.add(bondOfFlame.get().getTranslationKey());
        recipes.add(brightcrest.get().getTranslationKey());
        recipes.add(chaosRipper.get().getTranslationKey());
        recipes.add(circleOfLife.get().getTranslationKey());
        recipes.add(counterpoint.get().getTranslationKey());
        recipes.add(crabclaw.get().getTranslationKey());
        recipes.add(crownOfGuilt.get().getTranslationKey());
        recipes.add(darkerThanDark.get().getTranslationKey());
        recipes.add(darkgnaw.get().getTranslationKey());
        recipes.add(decisivePumpkin.get().getTranslationKey());
        recipes.add(destinysEmbrace.get().getTranslationKey());
        recipes.add(diamondDust.get().getTranslationKey());
        recipes.add(divewing.get().getTranslationKey());
        recipes.add(divineRose.get().getTranslationKey());
        //recipes.add(dreamShield.get().getTranslationKey());
        //recipes.add(dreamStaff.get().getTranslationKey());
        //recipes.add(dreamSword.get().getTranslationKey());
        recipes.add(dualDisc.get().getTranslationKey());
        recipes.add(earthshaker.get().getTranslationKey());
        recipes.add(endOfPain.get().getTranslationKey());
        recipes.add(endsOfTheEarth.get().getTranslationKey());
        recipes.add(fairyHarp.get().getTranslationKey());
        recipes.add(fairyStars.get().getTranslationKey());
        recipes.add(fatalCrest.get().getTranslationKey());
        recipes.add(fenrir.get().getTranslationKey());
        recipes.add(ferrisGear.get().getTranslationKey());
        recipes.add(followtheWind.get().getTranslationKey());
        recipes.add(frolicFlame.get().getTranslationKey());
        recipes.add(glimpseOfDarkness.get().getTranslationKey());
        recipes.add(guardianBell.get().getTranslationKey());
        recipes.add(guardianSoul.get().getTranslationKey());
        recipes.add(gullWing.get().getTranslationKey());
        recipes.add(herosCrest.get().getTranslationKey());
        recipes.add(hiddenDragon.get().getTranslationKey());
        recipes.add(hyperdrive.get().getTranslationKey());
        recipes.add(incompleteKiblade.get().getTranslationKey());
        recipes.add(jungleKing.get().getTranslationKey());
        recipes.add(keybladeOfPeoplesHearts.get().getTranslationKey());
        recipes.add(kiblade.get().getTranslationKey());
        recipes.add(kingdomKey.get().getTranslationKey());
        recipes.add(kingdomKeyD.get().getTranslationKey());
        recipes.add(knockoutPunch.get().getTranslationKey());
        recipes.add(ladyLuck.get().getTranslationKey());
        recipes.add(flameLiberator.get().getTranslationKey());
        recipes.add(gulasKeyblade.get().getTranslationKey());
        recipes.add(leviathan.get().getTranslationKey());
        recipes.add(lionheart.get().getTranslationKey());
        recipes.add(lostMemory.get().getTranslationKey());
        recipes.add(lunarEclipse.get().getTranslationKey());
        recipes.add(markOfAHero.get().getTranslationKey());
        recipes.add(mastersDefender.get().getTranslationKey());
        recipes.add(maverickFlare.get().getTranslationKey());
        recipes.add(metalChocobo.get().getTranslationKey());
        recipes.add(midnightRoar.get().getTranslationKey());
        recipes.add(mirageSplit.get().getTranslationKey());
        recipes.add(missingAche.get().getTranslationKey());
        recipes.add(monochrome.get().getTranslationKey());
        recipes.add(moogleOGlory.get().getTranslationKey());
        recipes.add(mysteriousAbyss.get().getTranslationKey());
        recipes.add(nightmaresEnd.get().getTranslationKey());
        recipes.add(nightmaresEndAndMirageSplit.get().getTranslationKey());
        recipes.add(noName.get().getTranslationKey());
        recipes.add(noNameBBS.get().getTranslationKey());
        recipes.add(oathkeeper.get().getTranslationKey());
        recipes.add(oblivion.get().getTranslationKey());
        recipes.add(oceansRage.get().getTranslationKey());
        recipes.add(olympia.get().getTranslationKey());
        recipes.add(omegaWeapon.get().getTranslationKey());
        recipes.add(ominousBlight.get().getTranslationKey());
        recipes.add(oneWingedAngel.get().getTranslationKey());
        recipes.add(painOfSolitude.get().getTranslationKey());
        recipes.add(photonDebugger.get().getTranslationKey());
        recipes.add(pixiePetal.get().getTranslationKey());
        recipes.add(pumpkinhead.get().getTranslationKey());
        recipes.add(rainfell.get().getTranslationKey());
        recipes.add(rejectionOfFate.get().getTranslationKey());
        recipes.add(royalRadiance.get().getTranslationKey());
        recipes.add(rumblingRose.get().getTranslationKey());
        recipes.add(shootingStar.get().getTranslationKey());
        recipes.add(signOfInnocence.get().getTranslationKey());
        recipes.add(silentDirge.get().getTranslationKey());
        recipes.add(skullNoise.get().getTranslationKey());
        recipes.add(sleepingLion.get().getTranslationKey());
        recipes.add(soulEater.get().getTranslationKey());
        recipes.add(spellbinder.get().getTranslationKey());
        recipes.add(starlight.get().getTranslationKey());
        recipes.add(starSeeker.get().getTranslationKey());
        recipes.add(stormfall.get().getTranslationKey());
        recipes.add(strokeOfMidnight.get().getTranslationKey());
        recipes.add(sweetDreams.get().getTranslationKey());
        recipes.add(sweetMemories.get().getTranslationKey());
        recipes.add(sweetstack.get().getTranslationKey());
        recipes.add(threeWishes.get().getTranslationKey());
        recipes.add(totalEclipse.get().getTranslationKey());
        recipes.add(treasureTrove.get().getTranslationKey());
        recipes.add(trueLightsFlight.get().getTranslationKey());
        recipes.add(twilightBlaze.get().getTranslationKey());
        recipes.add(twoBecomeOne.get().getTranslationKey());
        recipes.add(ultimaWeaponBBS.get().getTranslationKey());
        recipes.add(ultimaWeaponDDD.get().getTranslationKey());
        recipes.add(ultimaWeaponKH1.get().getTranslationKey());
        recipes.add(ultimaWeaponKH2.get().getTranslationKey());
        recipes.add(ultimaWeaponKH3.get().getTranslationKey());
        recipes.add(umbrella.get().getTranslationKey());
        recipes.add(unbound.get().getTranslationKey());
        recipes.add(irasKeyblade.get().getTranslationKey());
        recipes.add(acedsKeyblade.get().getTranslationKey());
        recipes.add(victoryLine.get().getTranslationKey());
        recipes.add(voidGear.get().getTranslationKey());
        recipes.add(avasKeyblade.get().getTranslationKey());
        recipes.add(waytotheDawn.get().getTranslationKey());
        recipes.add(waywardWind.get().getTranslationKey());
        recipes.add(winnersProof.get().getTranslationKey());
        recipes.add(wishingLamp.get().getTranslationKey());
        recipes.add(wishingStar.get().getTranslationKey());
        recipes.add(woodenKeyblade.get().getTranslationKey());
        recipes.add(woodenStick.get().getTranslationKey());
        recipes.add(youngXehanortsKeyblade.get().getTranslationKey());
        recipes.add(zeroOne.get().getTranslationKey());
        
       /* TODO for(String keyblade : MainConfig.items.bannedKeyblades) {
        	if(recipes.indexOf("item."+keyblade) >= 0)
        		recipes.remove(recipes.indexOf("item."+keyblade));
        }*/
    }

}